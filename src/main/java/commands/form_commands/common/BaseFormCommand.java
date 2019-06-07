package commands.form_commands.common;

import commands.person_commands.base.BaseCommand;
import model.db.DBProvider;
import model.roles.BasePerson;
import org.springframework.ui.Model;

import java.util.Map;

public abstract class BaseFormCommand {

    public final Map<String, String> request;
    public final Model response;
    public final Map<String, BasePerson> sessionParams;
    public final DBProvider dbProvider;
    public BaseCommand userCommand;

    public BaseFormCommand(Map<String, String> request, Model response, Map<String, BasePerson> sessionParams) {
        this.request = request;
        this.response = response;
        this.sessionParams = sessionParams;
        this.dbProvider = new DBProvider();
    }

    public abstract String execute();

}
