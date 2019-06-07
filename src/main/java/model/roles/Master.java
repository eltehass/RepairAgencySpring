package model.roles;

import model.extra.Invoice;

import java.io.Serializable;
import java.sql.SQLException;

public class Master extends BasePerson implements Serializable {

    public Master(int id, String name, String surname, int age, String login, String password, String email, String phone) {
        super(id,3, name, surname, age, login, password, email, phone);
    }

    public Master(String name, String surname, int age, String login, String password, String email, String phone) {
        super(3, name, surname, age, login, password, email, phone);
    }

    public String repair(int idInvoice) throws SQLException {
        Invoice invoice = dbProvider.invoiceDao().getEntityById(idInvoice);
        if (invoice == null) {
            throw new SQLException("Error: invoice with id = " + idInvoice + ", try with correct id");
        } else {
            invoice.setProgress(true);
            dbProvider.invoiceDao().update(idInvoice, invoice);
        }
        return "Invoice with id = " + idInvoice + " has been done";
    }

}