package com.samuelclinton.fiaparkapi.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.samuelclinton.fiaparkapi.domain.exception.ConflitoDeRecursoException;
import com.samuelclinton.fiaparkapi.domain.exception.ErroInternoException;
import com.samuelclinton.fiaparkapi.domain.exception.NegocioException;
import com.samuelclinton.fiaparkapi.domain.exception.RecursoNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. " +
            "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        final var status = HttpStatus.INTERNAL_SERVER_ERROR;
        final var problem = createProblemBuilder(status,
                ProblemType.ERRO_DE_SISTEMA, MSG_ERRO_GENERICA_USUARIO_FINAL).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
        final var status = HttpStatus.BAD_REQUEST;
        final var detail = ex.getMessage();
        final var problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, detail)
                .userMessage(detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<?> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex, WebRequest request) {
        final var status = HttpStatus.NOT_FOUND;
        final var detail = ex.getMessage();
        final var problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, detail)
                .userMessage(detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ConflitoDeRecursoException.class)
    public ResponseEntity<?> handleConflitoDeRecursoException(ConflitoDeRecursoException ex, WebRequest request) {
        final var status = HttpStatus.CONFLICT;
        final var detail = ex.getMessage();
        final var problem = createProblemBuilder(status, ProblemType.CONFLITO_DE_RECURSOS, detail)
                .userMessage(detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ErroInternoException.class)
    public ResponseEntity<?> handleErroInternoException(ErroInternoException ex, WebRequest request) {
        final var status = HttpStatus.INTERNAL_SERVER_ERROR;
        final var detail = ex.getMessage();
        final var problem = createProblemBuilder(status, ProblemType.ERRO_DE_SISTEMA, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatusCode status,
                                                                      WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers,
                    status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                   HttpHeaders headers, HttpStatusCode status,
                                                                   WebRequest request) {
        final var detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é do tipo inválido." +
                " Corrija e informe um valor compatível com o tipo %s",
                ex.getName(),
                ex.getValue(),
                ex.getParameter().getParameterType().getSimpleName());
        final var problem = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        } else if (rootCause instanceof MismatchedInputException) {
            return handleMismatchedInput((MismatchedInputException) rootCause, headers, status, request);
        }

        final var detail = "O corpo da requisição está inválido, verifique possível erro de sintaxe";
        final var problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleMismatchedInput(MismatchedInputException ex,
                                                         HttpHeaders headers, HttpStatusCode status,
                                                         WebRequest request) {
        final var path = joinPath(ex.getPath());
        final var detail = String.format("A propriedade '%s' recebeu um valor de tipo inválido." +
                " Tente novamente passando um valor do tipo %s.", path, ex.getTargetType().getSimpleName());
        final var problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,
                                                         HttpHeaders headers, HttpStatusCode status,
                                                         WebRequest request) {
        final var path = joinPath(ex.getPath());
        final var detail = String.format("A propriedade '%s' não existe. " +
                        "Corrija ou remova essa propriedade e tente novamente", path);
        final var problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
                                                       HttpStatusCode status, WebRequest request) {
        final var path = joinPath(ex.getPath());
        final var detail = String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido." +
                " Corrija e informe um valor compatível com o tipo %s.",
                path,
                ex.getValue(),
                ex.getTargetType().getSimpleName());
        final var problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatusCode status, WebRequest request) {
        final var detail = String.format("O recurso %s não existe", ex.getRequestURL());
        final var problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, detail)
                .userMessage(detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode status, WebRequest request) {
        if (body == null) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .status(status.value())
                    .title(parseHttpStatusCode(status).getReasonPhrase())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(OffsetDateTime.now())
                    .status(status.value())
                    .title((String) body)
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult, HttpHeaders headers,
                                                            HttpStatusCode status, WebRequest request) {

        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, ProblemType.DADOS_INVALIDOS, detail)
                .userMessage(detail)
                .objects(problemObjects)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }

    private HttpStatus parseHttpStatusCode(HttpStatusCode httpStatusCode) {
        return HttpStatus.valueOf(httpStatusCode.value());
    }

}
