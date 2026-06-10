package cl.previred.mantenedor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import cl.previred.mantenedor.config.OpenApiContactProperties;

@ConfigurationProperties(prefix = "swagger.info")
public record OpenApiProperties(
    String version,
    String name,
    String title,
    String description,
    OpenApiContactProperties contact){}