package mx.unison.data;
import mx.unison.models.Almacen;
import java.sql.SQLException;

public class AlmacenDao extends GenericDao<Almacen, Integer> {
    public AlmacenDao() throws SQLException {
        super(Almacen.class);
    }
}