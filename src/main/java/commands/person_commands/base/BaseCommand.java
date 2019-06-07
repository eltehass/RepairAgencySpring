package commands.person_commands.base;


import model.db.DBProvider;
import model.roles.BasePerson;

public abstract class BaseCommand implements Command {

    protected DBProvider dbProvider;
    protected BasePerson person;

    public BaseCommand(BasePerson person) {
        dbProvider = new DBProvider();
        this.person = person;
    }

    public BaseCommand() {
        dbProvider = new DBProvider();
        this.person = null;
    }

    public void setPerson(BasePerson person) {
        this.person = person;
    }
}