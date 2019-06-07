package model.roles;


import model.extra.Application;
import model.extra.UserResponse;

import java.io.Serializable;
import java.sql.SQLException;

public class Customer extends BasePerson implements Serializable {

    public Customer(int id, String name, String surname, int age, String login, String password, String email, String phone) {
        super(id,1, name, surname, age, login, password, email, phone);
    }

    public Customer(String name, String surname, int age, String login, String password, String email, String phone) {
        super(1, name, surname, age, login, password, email, phone);
    }

    public Application createApplication(int idPersonInfo, String appBody) throws SQLException {
        Application app = new Application(idPersonInfo, appBody);
        dbProvider.applicationDao().create(app);
        return app;
    }

    public String commentInvoice(int idInvoice, String comment) throws SQLException {
        UserResponse response = new UserResponse(idInvoice, comment);
        dbProvider.userResponsesDao().create(response);
        return "Customer with id = " + id + " commented invoice with id = " + idInvoice + " with message: " + comment;
    }

}