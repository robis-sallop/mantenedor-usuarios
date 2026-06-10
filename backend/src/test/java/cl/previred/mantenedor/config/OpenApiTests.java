package cl.previred.mantenedor.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
* OpenApiTest.
*
* @author  Roberto Salinas
* @version 1.0
*/
class OpenApiTests {

    @Test
    void properties_and_config_createBeans() {
        OpenApiContactProperties contact = new OpenApiContactProperties("Dev","dev@example.com");
        OpenApiProperties props = new OpenApiProperties("1.0","name","title","desc", contact);

        assertEquals("title", props.title());
        assertEquals("dev@example.com", props.contact().email());

        OpenApiConfig cfg = new OpenApiConfig();
        var openApi = cfg.customOpenAPI(props);
        assertNotNull(openApi.getInfo());
        assertEquals("title", openApi.getInfo().getTitle());

        var resp = cfg.apiResponse();
        assertNotNull(resp);
    }
}
