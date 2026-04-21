package mx.unison;

import mx.unison.utils.CryptoUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CryptoUtilsTest {
    @Test
    public void testMd5Hash() {
        String original = "admin123";
        // Este es el hash correcto para "admin123" usando UTF-8
        String esperado = "0192023a7bbd73250516f069df18b500";

        assertEquals(esperado, CryptoUtils.md5(original),
                "El hash para admin123 no coincide");
    }
}