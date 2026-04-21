package mx.unison;

import mx.unison.models.Almacen;
import mx.unison.models.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class DatabaseTest {
    private Database db;

    @BeforeEach
    public void setUp() {
        db = new Database();
    }

    @Test
    public void testLoginExitoso() {
        Usuario u = db.authenticate("ADMIN", "admin123");
        assertNotNull(u, "El usuario ADMIN debería existir");
    }

    @Test
    public void testGestionAlmacen() {
        db.insertAlmacen("Test Bodega", "Calle 123", "JUnit");
        List<Almacen> lista = db.listAlmacenes();
        boolean existe = lista.stream().anyMatch(a -> a.nombre.equals("Test Bodega"));
        assertTrue(existe, "El almacén no se guardó correctamente");
    }
} 