package cl.previred.mantenedor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración básica de OpenAPI (Swagger) para la API de usuarios.
 * Expone metadatos como título, versión y descripción que se muestran en Swagger UI.
 *
 * @author Roberto Salinas
 * @version 1.0
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mantenedor de Usuarios API")
                        .version("0.0.1")
                        .description("API REST simple para crear, listar, actualizar y eliminar usuarios.")
                        .contact(new Contact().name("Roberto Salinas").email("roberto.ismael90@gmail.com"))
                );
    }
}

