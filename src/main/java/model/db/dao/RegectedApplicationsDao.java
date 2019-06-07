package model.db.dao;



import model.extra.RegectedApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class RegectedApplicationsDao extends AbstractDao<RegectedApplication, Integer> {

    public RegectedApplicationsDao(Connection connection) { super(connection); }

    @Override
    public List<RegectedApplication> getAll() throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM rejected_applications");
        List<RegectedApplication> result = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_application = resultSet.getInt(2);
                int id_manager = resultSet.getInt(3);
                String rejectedMessage = resultSet.getString(4);
                result.add(new RegectedApplication(id, id_application, id_manager, rejectedMessage));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }

    public List<RegectedApplication> getAllForCustomer(Integer idCustomer) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM rejected_applications WHERE (id_application in (SELECT id FROM applications WHERE id_person_info = ?))");
        List<RegectedApplication> result = new ArrayList<>();

        try {
            preparedStatement.setInt(1, idCustomer);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_application = resultSet.getInt(2);
                int id_manager = resultSet.getInt(3);
                String rejectedMessage = resultSet.getString(4);
                result.add(new RegectedApplication(id, id_application, id_manager, rejectedMessage));
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }



    @Override
    public RegectedApplication getEntityById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM rejected_applications WHERE id = ?");
        RegectedApplication rejectedApplication = null;

        try {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id_application = resultSet.getInt(2);
                int id_manager = resultSet.getInt(3);
                String rejectedMessage = resultSet.getString(4);
                rejectedApplication = new RegectedApplication(id, id_application, id_manager, rejectedMessage);
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return rejectedApplication;
    }

    @Override
    public boolean update(Integer id, RegectedApplication entity) throws SQLException {
        return handleQuery(
                "UPDATE rejected_applications SET id_application = ?, id_manager = ?, reject__message = ? WHERE id = ?" ,
                entity.idApplication, entity.idManager, entity.message, id
        );
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return handleQuery(
                "DELETE FROM rejected_applications WHERE id = ?",
                id
        );
    }

    @Override
    public boolean create(RegectedApplication entity) throws SQLException {
        return handleQuery(
                "INSERT INTO rejected_applications (id_application, id_manager, reject__message) VALUES (?, ?, ?)",
                entity.idApplication, entity.idManager, entity.message
        );
    }
}
