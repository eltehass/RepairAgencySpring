package commands.person_commands.customer;


import commands.person_commands.base.BaseCommand;
import model.roles.Customer;

import java.sql.SQLException;

public class CommentCommand extends BaseCommand {

    public int idInvoice;
    public String comment;

    public CommentCommand(Customer customer, int idInvoice, String comment) {
        super(customer);
        this.idInvoice = idInvoice;
        this.comment = comment;
    }

    public CommentCommand() { super(); }

    @Override
    public String execute() throws SQLException {
        ((Customer) person).commentInvoice(idInvoice, comment);
        return "Invoice with id = " + idInvoice + " got comment: " + comment;
    }
}
