package commands.person_commands.master;


import commands.person_commands.base.BaseCommand;
import model.roles.Master;

import java.sql.SQLException;

public class HandleCommand extends BaseCommand {

    public int idInvoice;

    public HandleCommand(Master master, int idInvoice) {
        super(master);
        this.idInvoice = idInvoice;
    }

    public HandleCommand() { super(); }

    @Override
    public String execute() throws SQLException {
        ((Master) person).repair(idInvoice);
        return "Master with id = " + person.id + " has been finished invoice with id = " + idInvoice;
    }
}
