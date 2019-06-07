package model.db.dao;


import model.extra.UserResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class UserResponsesDao extends AbstractDao<UserResponse, Integer> {

    public UserResponsesDao(Connection connection) { super(connection); }

    @Override
    public List<UserResponse> getAll() throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM user_responses");
        List<UserResponse> result = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_invoice = resultSet.getInt(2);
                String comment = resultSet.getString(3);
                result.add(new UserResponse(id, id_invoice, comment));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }

    public List<UserResponse> getAllForMaster(Integer masterId) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM user_responses WHERE (id_invoice in (SELECT id FROM invoices WHERE id_master = ?))");
        List<UserResponse> result = new ArrayList<>();

        try {
            preparedStatement.setInt(1, masterId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_invoice = resultSet.getInt(2);
                String comment = resultSet.getString(3);
                result.add(new model.extra.UserResponse(id, id_invoice, comment));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }


    @Override
    public UserResponse getEntityById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM user_responses WHERE id = ?");
        UserResponse response = null;

        try {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id_invoice = resultSet.getInt(2);
                String comment = resultSet.getString(3);
                response = new UserResponse(id, id_invoice, comment);
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return response;
    }

    @Override
    public boolean update(Integer id, UserResponse entity) throws SQLException {
        return handleQuery(
                "UPDATE user_responses SET id_invoice = ?, comment = ? WHERE id = ?" ,
                entity.idInvoice, entity.comment, id
        );
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return handleQuery(
                "DELETE FROM user_responses WHERE id = ?",
                id
        );
    }

    @Override
    public boolean create(UserResponse entity) throws SQLException {
        return handleQuery(
                "INSERT INTO user_responses (id_invoice, comment) VALUES (?, ?)",
                entity.idInvoice, entity.comment
        );
    }
}
