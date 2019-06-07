package commands.form_commands.manager;

import commands.form_commands.common.BaseFormCommand;
import commands.person_commands.manager.UnacceptCommand;
import model.extra.Application;
import model.roles.BasePerson;
import model.roles.Manager;
import org.springframework.ui.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UnacceptFormCommand extends BaseFormCommand {


    public UnacceptFormCommand(Map<String, String> request, Model response, Map<String, BasePerson> sessionParams) {
        super(request, response, sessionParams);
    }

    @Override
    public String execute() {
        List<Application> unhandledApplications = new ArrayList<>();
        List<BasePerson> masters = new ArrayList<>();

        try {
            unhandledApplications = dbProvider.applicationDao().getAllNotHandled();
            masters = dbProvider.personInfoDao().getAllMasters();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.addAttribute("unhandled_applications", unhandledApplications);
        response.addAttribute("masters", masters);


        Manager user = (Manager) sessionParams.get("user");

        response.addAttribute("usrName", user.name);
        response.addAttribute("usrSurname", user.surname);

        String appStr = request.get("managerInputAppId");

        if (appStr == null || appStr.isEmpty()) {
            response.addAttribute("message", "Error: appId field is empty");

            return "/home_manager";
        }

        int appId = Integer.parseInt(appStr);
        String message = request.get("managerInputMessage");

        if (!message.isEmpty()) {
            if (!unhandledApplications.stream().map(app -> app.id).collect(Collectors.toList()).contains(appId)) {
                response.addAttribute("message", "You can't decline app with id = " + appId + ", because it was handled before or doesn't exists");

                return "/home_manager";
            }

            userCommand = new UnacceptCommand(user, appId, message);
            try {
                userCommand.execute();

                try {
                    unhandledApplications = dbProvider.applicationDao().getAllNotHandled();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                response.addAttribute("unhandled_applications", unhandledApplications);
                response.addAttribute("message", "Application with id = " + appId + " was unaccepted with message: " + message);
            } catch (SQLException e) {
                e.printStackTrace();
                response.addAttribute("message", "Error: try correct appId, managerId and message");
            }
        } else {
            response.addAttribute("message", "Incorrect input data for Unaccept action");
        }

        return "/home_manager";
    }

}