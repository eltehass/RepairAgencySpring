package model.roles;


import model.extra.Invoice;
import model.extra.RegectedApplication;

import java.io.Serializable;
import java.sql.SQLException;

public class Manager extends BasePerson implements Serializable {

    public Manager(int id, String name, String surname, int age, String login, String password, String email, String phone) {
        super(id,2, name, surname, age, login, password, email, phone);
    }

    public Manager(String name, String surname, int age, String login, String password, String email, String phone) {
        super(2, name, surname, age, login, password, email, phone);
    }

    public String acceptApplication(int applicationId, int idMaster, int price) throws SQLException {
        BasePerson master = (BasePerson) dbProvider.personInfoDao().getEntityById(idMaster);
        PersonType masterType = PersonType.NONE;

        if (master != null) {
            masterType = dbProvider.personTypeDao().getEntityById(master.idPersonType);
        }

        if (masterType.equals(PersonType.MASTER))
            dbProvider.invoiceDao().create(new Invoice(applicationId, idMaster, price));
        else
            throw new SQLException("");

        return "App was accepted with id = " + applicationId;
    }

    public String declineApplication(int applicationId, int idManager, String message) throws SQLException {
        dbProvider.regectedApplicationsDao().create(new RegectedApplication(applicationId, idManager, message));
        return "App was rejected with id = " + applicationId + " and  message: " + message;
    }
}