package br.edu.ibmec.cloud.ecommerce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI apiDocConfig() {
         return new OpenAPI()
                .info(new Info()
                        .title("IBMEC - PROJETO CLOUD")
                        .description("API de Gerencia e Checkout de Produto - Projeto Cloud & Big Data")
                        .version("1.0")
                        .contact(new Contact()
                                .email("larissa.amn@hotmail.com")
                                .name("Larissa Nobrega")
                        )
                );
    }

}
