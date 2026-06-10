package cl.previred.mantenedor.web.exception;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleValidation_buildsApiError() {
        BeanPropertyBindingResult br = new BeanPropertyBindingResult(new Object(), "obj");
        br.addError(new FieldError("obj", "field", "must not be blank"));

        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(br);

        var resp = handler.handleValidation(ex);
        assertEquals(400, resp.getBody().status());
        assertTrue(resp.getBody().message().contains("field"));
    }

    @Test
    void handleNotFound_returnsNotFound() {
        var resp = handler.handleNotFound(new IllegalArgumentException("not found"));
        assertEquals(404, resp.getBody().status());
        assertEquals("not found", resp.getBody().message());
    }

    @Test
    void handleConflict_returnsConflict() {
        var resp = handler.handleConflict(new DataIntegrityViolationException("dup"));
        assertEquals(409, resp.getBody().status());
        assertEquals("dup", resp.getBody().message());
    }

    @Test
    void handleAll_returnsInternalServerError() {
        var resp = handler.handleAll(new RuntimeException("boom"));
        assertEquals(500, resp.getBody().status());
        assertEquals("boom", resp.getBody().message());
    }
}
