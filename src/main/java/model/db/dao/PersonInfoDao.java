package model.db.dao;


import model.roles.BasePerson;
import model.roles.Customer;
import model.roles.Manager;
import model.roles.Master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class PersonInfoDao extends AbstractDao<BasePerson, Integer> {

    public PersonInfoDao(Connection connection) { super(connection); }

    @Override
    public List<BasePerson> getAll() throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM person_info");
        List<BasePerson> result = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_person_type = resultSet.getInt(2);
                String name = resultSet.getString(3);
                String surname = resultSet.getString(4);
                int age = resultSet.getInt(5);
                String login = resultSet.getString(6);
                String password = resultSet.getString(7);
                String email = resultSet.getString(8);
                String phone = resultSet.getString(9);

                if (id_person_type == 1) {
                    result.add(new Customer(id, name,surname,age,login,password,email,phone));
                }

                if (id_person_type == 2) {
                    result.add(new Manager(id, name,surname,age,login,password,email,phone));
                }

                if (id_person_type == 3) {
                    result.add(new Master(id, name,surname,age,login,password,email,phone));
                }
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }

    public List<BasePerson> getAllMasters() throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM person_info WHERE id_person_type = 3");
        List<BasePerson> result = new ArrayList<>();

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_person_type = resultSet.getInt(2);
                String name = resultSet.getString(3);
                String surname = resultSet.getString(4);
                int age = resultSet.getInt(5);
                String login = resultSet.getString(6);
                String password = resultSet.getString(7);
                String email = resultSet.getString(8);
                String phone = resultSet.getString(9);

                if (id_person_type == 1) {
                    result.add(new Customer(id, name,surname,age,login,password,email,phone));
                }

                if (id_person_type == 2) {
                    result.add(new Manager(id, name,surname,age,login,password,email,phone));
                }

                if (id_person_type == 3) {
                    result.add(new Master(id, name,surname,age,login,password,email,phone));
                }
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return result;
    }

    @Override
    public BasePerson getEntityById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM person_info WHERE id = ?");
        BasePerson person = null;

        try {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id_person_type = resultSet.getInt(2);
                String name = resultSet.getString(3);
                String surname = resultSet.getString(4);
                int age = resultSet.getInt(5);
                String login = resultSet.getString(6);
                String password = resultSet.getString(7);
                String email = resultSet.getString(8);
                String phone = resultSet.getString(9);

                if (id_person_type == 1) {
                    person = new Customer(id, name,surname,age,login,password,email,phone);
                }

                if (id_person_type == 2) {
                    person = new Manager(id, name,surname,age,login,password,email,phone);
                }

                if (id_person_type == 3) {
                    person = new Master(id, name,surname,age,login,password,email,phone);
                }
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return person;
    }

    public BasePerson checkIfExist(String login, String password, String userName, String userSurname) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM person_info WHERE login = ? OR (name = ? AND surname = ?)");
        BasePerson person = null;

        try {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, userSurname);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_person_type = resultSet.getInt(2);
                String name = resultSet.getString(3);
                String surname = resultSet.getString(4);
                int age = resultSet.getInt(5);
                String email = resultSet.getString(8);
                String phone = resultSet.getString(9);

                if (id_person_type == 1) {
                    person = new Customer(id, name,surname,age,login,password,email,phone);
                }

                if (id_person_type == 2) {
                    person = new Manager(id, name,surname,age,login,password,email,phone);
                }

                if (id_person_type == 3) {
                    person = new Master(id, name,surname,age,login,password,email,phone);
                }
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return person;
    }

    public BasePerson logIn(String login, String password) throws SQLException {
        PreparedStatement preparedStatement = getPrepareStatement("SELECT * FROM person_info WHERE login = ? AND password = ?");
        BasePerson person = null;

        try {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                int id_person_type = resultSet.getInt(2);
                String name = resultSet.getString(3);
                String surname = resultSet.getString(4);
                int age = resultSet.getInt(5);
                String email = resultSet.getString(8);
                String phone = resultSet.getString(9);

                if (id_person_type == 1) {
                    person = new Customer(id, name,surname,age,login,password,email,phone);
                }

                if (id_person_type == 2) {
                    person = new Manager(id, name,surname,age,login,password,email,phone);
                }

                if (id_person_type == 3) {
                    person = new Master(id, name,surname,age,login,password,email,phone);
                }
            }
        } finally {
            closePrepareStatement(preparedStatement);
        }

        return person;
    }

    @Override
    public boolean update(Integer id, BasePerson entity) throws SQLException {
        return handleQuery(
                "UPDATE person_info SET id_person_type = ?, name = ?, surname = ?, age = ?, login = ?, password = ?, email = ?, phone = ? WHERE id = ?" ,
                entity.idPersonType, entity.name, entity.surname, entity.age, entity.login, entity.password, entity.email, entity.phone, id
        );
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        return handleQuery(
                "DELETE FROM person_info WHERE id = ?",
                id
        );
    }

    @Override
    public boolean create(BasePerson entity) throws SQLException {
        return handleQuery(
                "INSERT INTO person_info (id_person_type, name, surname, age, login, password, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                entity.idPersonType, entity.name, entity.surname, entity.age, entity.login, entity.password, entity.email, entity.phone
        );
    }
}
