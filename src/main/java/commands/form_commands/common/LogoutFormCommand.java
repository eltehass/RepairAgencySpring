package commands.form_commands.common;

import model.roles.BasePerson;
import org.springframework.ui.Model;

import java.util.Map;

public class LogoutFormCommand extends BaseFormCommand {


    public LogoutFormCommand(Map<String, String> request, Model response, Map<String, BasePerson> sessionParams) {
        super(request, response, sessionParams);
    }

    @Override
    public String execute() {
//        response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
//        response.setHeader("Progma","no-cache");
//        response.setDateHeader("Expires", 0);
//
//        request.getSession().removeAttribute("user");
//        request.getSession(false);
//        request.getSession().setAttribute("user", null);
//        request.getSession().invalidate();
        sessionParams.remove("user");
        sessionParams.put("user", null);

        return "/login";
    }
}
