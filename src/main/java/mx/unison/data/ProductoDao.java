package mx.unison.data;
import mx.unison.models.Producto;
import java.sql.SQLException;

public class ProductoDao extends GenericDao<Producto, Integer> {
    public ProductoDao() throws SQLException {
        super(Producto.class);
    }
}