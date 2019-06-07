package model.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDao<E, K> {
    private Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public abstract List<E> getAll() throws SQLException;
    public abstract E getEntityById(K id) throws SQLException;
    public abstract boolean update(K id, E entity) throws SQLException;
    public abstract boolean delete(K id) throws SQLException;
    public abstract boolean create(E entity) throws SQLException;

    // Получение экземпляра PrepareStatement
    public PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ps;
    }

    // Закрытие PrepareStatement
    public void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean handleQuery(String query, Object ... args) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement(query);

        try {
            int counter = 1;

            for (Object arg : args) {
                if (arg instanceof Integer) {
                    preparedStatement.setInt(counter, (Integer) arg);
                    counter++;
                }
                if (arg instanceof String) {
                    preparedStatement.setString(counter, (String) arg);
                    counter++;
                }
            }

            preparedStatement.executeUpdate();
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return true;
    }
}