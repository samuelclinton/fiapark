package com.samuelclinton.fiaparkapi.core.springdoc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        var tags = new ArrayList<Tag>();
        tags.add(new Tag().name("Condutores").description("Gerencia os condutores"));
        tags.add(new Tag().name("Veículos").description("Gerencia os veículos"));
        tags.add(new Tag().name("Estacionamentos").description("Gerencia os estacionamentos"));
        return new OpenAPI()
                .info(new Info()
                        .title("Fiapark API")
                        .version("v1")
                        .description("API da da aplicação de parquímetros Fiapark"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório")
                        .url("https://github.com/samuelclinton/fiapark"))
                .tags(tags);
    }

}
