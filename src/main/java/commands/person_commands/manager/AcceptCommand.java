package commands.person_commands.manager;


import commands.person_commands.base.BaseCommand;
import model.roles.Manager;

import java.sql.SQLException;

public class AcceptCommand extends BaseCommand {

    public int idApplication;
    public int idMaster;
    public int price;

    public AcceptCommand(Manager manager, int idApplication, int idMaster, int price) {
        super(manager);
        this.idApplication = idApplication;
        this.idMaster = idMaster;
        this.price = price;
    }

    public AcceptCommand() { super(); }

    @Override
    public String execute() throws SQLException {
        ((Manager) person).acceptApplication(idApplication, idMaster, price);
        return "Manager with id = " + person.id + " accepted application with id = " + idApplication;
    }

}
