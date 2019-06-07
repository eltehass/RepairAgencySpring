package model.db.dao;

import model.roles.PersonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PersonTypeDao extends AbstractDao<PersonType, Integer> {

    public PersonTypeDao(Connection connection) { super(connection); }

    @Override
    public List<PersonType> getAll() throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM person_type");
        List result = new ArrayList<PersonType>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);

                if (id == 1) {
                    result.add(PersonType.CUSTOMER);
                }

                if (id == 2) {
                    result.add(PersonType.MANAGER);
                }

                if (id == 3) {
                    result.add(PersonType.MASTER);
                }
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }

    @Override
    public PersonType getEntityById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM person_type WHERE id = ?");
        PersonType type = PersonType.NONE;

        try {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (id == 1) {
                    type = PersonType.CUSTOMER;
                }

                if (id == 2) {
                    type = PersonType.MANAGER;
                }

                if (id == 3) {
                    type = PersonType.MASTER;
                }
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return type;
    }

    @Override
    public boolean update(Integer id, PersonType entity) throws SQLException {
        return handleQuery(
                "UPDATE person_type SET type = ? WHERE id = ?" ,
                entity.toString(), id
        );
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return handleQuery(
                "DELETE FROM person_type WHERE id = ?",
                id
        );
    }

    @Override
    public boolean create(PersonType entity) throws SQLException {
        return handleQuery(
                "INSERT INTO person_type (type) VALUES (?)",
                entity.toString()
        );
    }
}
