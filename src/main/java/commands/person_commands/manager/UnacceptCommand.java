package commands.person_commands.manager;


import commands.person_commands.base.BaseCommand;
import model.roles.Manager;

import java.sql.SQLException;

public class UnacceptCommand extends BaseCommand {

    public int idApplication;
    public int idManager;
    public String message;

    public UnacceptCommand(Manager manager, int idApplication, String message) {
        super(manager);
        this.idApplication = idApplication;
        this.idManager = manager.id;
        this.message = message;
    }

    public UnacceptCommand() { super(); }

    @Override
    public String execute() throws SQLException {
        ((Manager) person).declineApplication(idApplication, idManager, message);
        return "Manager has declined application with id = " + idApplication;
    }

}
