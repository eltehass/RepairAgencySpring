package commands.form_commands.customer;

import commands.form_commands.common.BaseFormCommand;
import commands.person_commands.customer.CreateCommand;
import model.extra.Invoice;
import model.extra.RegectedApplication;
import model.roles.BasePerson;
import model.roles.Customer;
import org.springframework.ui.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateFormCommand extends BaseFormCommand {


    public CreateFormCommand(Map<String, String> request, Model response, Map<String, BasePerson> sessionParams) {
        super(request, response, sessionParams);
    }

    @Override
    public String execute() {
        Customer user = (Customer) sessionParams.get("user");

        List<Invoice> invoices = new ArrayList<>();
        List<RegectedApplication> regectedApplications = new ArrayList<>();

        try {
            invoices = dbProvider.invoiceDao().getFinishedForCustomer(user.id);
            regectedApplications = dbProvider.regectedApplicationsDao().getAllForCustomer(user.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.addAttribute("usrName", user.name);
        response.addAttribute("usrSurname", user.surname);

        response.addAttribute("finished_invoices", invoices);
        response.addAttribute("rejected_applications", regectedApplications);

        String body = request.get("customerInputBody");

        if (!body.isEmpty()) {
            userCommand = new CreateCommand(user, body);
            try {
                userCommand.execute();
                response.addAttribute("message", "Application was created with body = " + body);
            } catch (SQLException e) {
                e.printStackTrace();
                response.addAttribute("message", "Error: try correct body");
            }
        } else {
            response.addAttribute("message", "Incorrect input data for Create action");
        }

        return "/home_customer";
    }

}
