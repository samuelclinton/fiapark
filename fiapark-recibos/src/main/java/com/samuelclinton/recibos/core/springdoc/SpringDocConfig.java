package com.samuelclinton.recibos.core.springdoc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fiapark Recibos")
                        .version("v1")
                        .description("Microsserviço de recibos da aplicação Fiapark"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório")
                        .url("https://github.com/samuelclinton/fiapark"))
                ;
    }

}
