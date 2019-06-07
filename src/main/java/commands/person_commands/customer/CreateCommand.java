package commands.person_commands.customer;


import commands.person_commands.base.BaseCommand;
import model.roles.Customer;

import java.sql.SQLException;

public class CreateCommand extends BaseCommand {

    public String body;

    public CreateCommand(Customer customer, String body) {
        super(customer);
        this.body = body;
    }

    public CreateCommand() { super(); }

    @Override
    public String execute() throws SQLException {
        ((Customer) person).createApplication(person.id, body);
        return "App was created by customer with id = " + person.id;
    }
}
