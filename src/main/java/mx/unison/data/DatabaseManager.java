package mx.unison.data;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import mx.unison.models.*;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:inventario_v2.db";
    private static ConnectionSource connectionSource;

    public static ConnectionSource getConnection() throws SQLException {
        if (connectionSource == null) {
            connectionSource = new JdbcConnectionSource(URL);
            // Crear tablas automáticamente si no existen
            TableUtils.createTableIfNotExists(connectionSource, Usuario.class);
            TableUtils.createTableIfNotExists(connectionSource, Almacen.class);
            TableUtils.createTableIfNotExists(connectionSource, Producto.class);
        }
        return connectionSource;
    }
}