package mx.unison.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.List;

public class GenericDao<T, ID> {
    protected Dao<T, ID> dao;

    public GenericDao(Class<T> clazz) throws SQLException {
        ConnectionSource source = DatabaseManager.getConnection();
        this.dao = DaoManager.createDao(source, clazz);
    }

    public void create(T data) throws SQLException { dao.create(data); }
    public void update(T data) throws SQLException { dao.update(data); }
    public void delete(T data) throws SQLException { dao.delete(data); }
    public T queryForId(ID id) throws SQLException { return dao.queryForId(id); }
    public List<T> queryForAll() throws SQLException { return dao.queryForAll(); }
}