package commands.form_commands.manager;

import commands.form_commands.common.BaseFormCommand;
import commands.person_commands.manager.AcceptCommand;
import model.extra.Application;
import model.roles.BasePerson;
import model.roles.Manager;
import org.springframework.ui.Model;

import javax.servlet.RequestDispatcher;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AcceptFormCommand extends BaseFormCommand {


    public AcceptFormCommand(Map<String, String> request, Model response, Map<String, BasePerson> sessionParams) {
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
        String masterStr = request.get("managerInputMasterId");
        String priceStr = request.get("managerInputPrice");

        if (appStr == null || masterStr == null || priceStr == null || appStr.isEmpty() || masterStr.isEmpty() || priceStr.isEmpty()) {
            response.addAttribute("message", "Error: one or some of fields: appId, masterId and price are empty");

            return "/home_manager";
        }

        int appId = Integer.parseInt(appStr);
        int masterId = Integer.parseInt(masterStr);
        int price = Integer.parseInt(priceStr);

        userCommand = new AcceptCommand(user, appId, masterId, price);
        try {
            if (!unhandledApplications.stream().map(app -> app.id).collect(Collectors.toList()).contains(appId)) {
                response.addAttribute("message", "You can't accept app with id = " + appId + ", because it was handled before or doesn't exists");

                return "/home_manager";
            }

            userCommand.execute();

            try {
                unhandledApplications = dbProvider.applicationDao().getAllNotHandled();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response.addAttribute("unhandled_applications", unhandledApplications);
            response.addAttribute("message", "Application with id = " + appId + " was accepted with price = " + price);
        } catch (SQLException e) {
            e.printStackTrace();
            response.addAttribute("message", "Error: try correct appId, masterId and price");
        }

        return "/home_manager";
    }

}
