package model.db.dao;


import model.extra.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class ApplicationDao extends AbstractDao<Application, Integer> {

    public ApplicationDao(Connection connection) { super(connection); }

    @Override
    public List<Application> getAll() throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM applications");
        List<Application> result = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_person_info = resultSet.getInt(2);
                String body = resultSet.getString(3);
                result.add(new Application(id, id_person_info, body));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }

    public List<Application> getAllByCustomerId(Integer userId) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM applications WHERE id_person_info = ?");
        List<Application> result = new ArrayList<>();

        try {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_person_info = resultSet.getInt(2);
                String body = resultSet.getString(3);
                result.add(new Application(id, id_person_info, body));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }


    public List<Application> getAllNotHandled() throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM applications WHERE ((id NOT IN (SELECT id_application FROM invoices)) AND (id NOT IN (SELECT id_application FROM rejected_applications)))");
        List<Application> result = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_person_info = resultSet.getInt(2);
                String body = resultSet.getString(3);
                result.add(new Application(id, id_person_info, body));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }


    @Override
    public Application getEntityById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM applications WHERE id = ?");
        Application application = null;

        try {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id_person_info = resultSet.getInt(2);
                String body = resultSet.getString(3);
                application = new Application(id, id_person_info, body);
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return application;
    }

    @Override
    public boolean update(Integer id, Application entity) throws SQLException {
        return handleQuery(
                "UPDATE applications SET id_person_info = ?, body = ? WHERE id = ?" ,
                entity.idPersonInfo, entity.body, id
        );
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return handleQuery(
                "DELETE FROM applications WHERE id = ?",
                id
        );
    }

    @Override
    public boolean create(Application entity) throws SQLException {
        return handleQuery(
                "INSERT INTO applications (id_person_info, body) VALUES (?, ?)",
                entity.idPersonInfo, entity.body
        );
    }
}