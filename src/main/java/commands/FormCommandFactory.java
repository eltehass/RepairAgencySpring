package commands;

import commands.form_commands.common.BaseFormCommand;
import commands.form_commands.common.LoginFormCommand;
import commands.form_commands.common.LogoutFormCommand;
import commands.form_commands.common.RegisterFormCommand;
import commands.form_commands.customer.CommentFormCommand;
import commands.form_commands.customer.CreateFormCommand;
import commands.form_commands.manager.AcceptFormCommand;
import commands.form_commands.manager.UnacceptFormCommand;
import commands.form_commands.master.HandleFormCommand;
import model.roles.BasePerson;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

public class FormCommandFactory {
    public final Map<String, BaseFormCommand> commands = new HashMap<>();

    public FormCommandFactory(Map<String, String> request, Model response, Map<String, BasePerson> sessionParams) {
        commands.put("loginCommand",    new LoginFormCommand(request,response,sessionParams));
        commands.put("registerCommand", new RegisterFormCommand(request,response,sessionParams));
        commands.put("commentCommand",  new CommentFormCommand(request,response,sessionParams));
        commands.put("createCommand",   new CreateFormCommand(request,response,sessionParams));
        commands.put("acceptCommand",   new AcceptFormCommand(request,response,sessionParams));
        commands.put("declineCommand",  new UnacceptFormCommand(request,response,sessionParams));
        commands.put("handleCommand",   new HandleFormCommand(request,response,sessionParams));
        commands.put("logoutCommand",   new LogoutFormCommand(request,response,sessionParams));
    }

    public BaseFormCommand getCommand(String command){
        return commands.getOrDefault(command, null);
    }
}
