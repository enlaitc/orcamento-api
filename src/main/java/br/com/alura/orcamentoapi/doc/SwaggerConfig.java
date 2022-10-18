package br.com.alura.orcamentoapi.doc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .pathsToExclude("/auth/**")
                .pathsToExclude("/usuarios/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-api")
                .pathsToMatch("/usuarios/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth-api")
                .pathsToMatch("/auth/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI(){
        return new OpenAPI()
                .info(
                        new Info().title("Orçamento Api")
                                .description("API para gerenciar orçamento familiar")
                                .version("v1.0")
                                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Orcamento Api Docs")
                                .url("https://github.com/enlaitc/orcamento-api")
                );
    }
}
