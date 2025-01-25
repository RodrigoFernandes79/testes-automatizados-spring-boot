package br.com.rodrigo.rest_with_spring_boot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI customOpenApiConfig() {
        return new OpenAPI().info(new Info()
                .title("Hello Swagger APi")
                .version("v1")
                .description("Api de gerenciamento de Pessoas")
                .license(new License().name("Apache"))
        );

    }
}
