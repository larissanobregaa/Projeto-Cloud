package br.edu.ibmec.projeto_cloud;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI apiDocConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("IBMEC - PROJETO CLOUD")
                        .description("API de Autorização do Cartão de Credito - Projeto Cloud & Big Data")
                        .version("1.0")
                        .contact(new Contact()
                                .email("larissa.amn@hotmail.com")
                                .name("Larissa Nobrega")
                        )
                );
    }
}
