package cl.previred.mantenedor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración básica de OpenAPI (Swagger) para la API de usuarios.
 * Expone metadatos como título, versión y descripción que se muestran en Swagger UI.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(OpenApiProperties properties) {
        return new OpenAPI()
                .info(new Info()
                        .title(properties.title())
                        .version(properties.version())
                        .description(properties.description())
                        .contact(new Contact()
                                .name(properties.contact().name())
                                .email(properties.contact().email()))
                );
    }
    @Bean
    public ApiResponse apiResponse(){ return new ApiResponse(); }
}

