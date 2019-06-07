package commands.form_commands.master;

import commands.form_commands.common.BaseFormCommand;
import commands.person_commands.master.HandleCommand;
import model.extra.Invoice;
import model.extra.UserResponse;
import model.roles.BasePerson;
import model.roles.Master;
import org.springframework.ui.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandleFormCommand extends BaseFormCommand {


    public HandleFormCommand(Map<String, String> request, Model response, Map<String, BasePerson> sessionParams) {
        super(request, response, sessionParams);
    }

    @Override
    public String execute() {
        Master user = (Master) sessionParams.get("user");

        List<Invoice> undoneInvoices = new ArrayList<>();
        List<UserResponse> userResponses = new ArrayList<>();

        try {
            undoneInvoices = dbProvider.invoiceDao().getNotDoneForMaster(user.id);
            userResponses = dbProvider.userResponsesDao().getAllForMaster(user.id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.addAttribute("undone_invoices", undoneInvoices);
        response.addAttribute("user_responses", userResponses);

        response.addAttribute("usrName", user.name);
        response.addAttribute("usrSurname", user.surname);


        String invoiceStr = request.get("inputInvoiceId");

        if (invoiceStr == null || invoiceStr.isEmpty()) {
            response.addAttribute("message", "Error: invoiceId field is empty");

            return "/home_master";
        }

        int invoiceId = Integer.parseInt(invoiceStr);

        userCommand = new HandleCommand(user, invoiceId);
        if (!undoneInvoices.stream().map(invoice -> invoice.id).collect(Collectors.toList()).contains(invoiceId)) {
            response.addAttribute("message", "You can't handle invoice with id = " + invoiceId + ", because it was handled before, or it was set for another master, or doesn't exists");

            return "/home_master";
        }

        try {
            userCommand.execute();

            try {
                undoneInvoices = dbProvider.invoiceDao().getNotDoneForMaster(user.id);
                userResponses = dbProvider.userResponsesDao().getAllForMaster(user.id);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            response.addAttribute("undone_invoices", undoneInvoices);
            response.addAttribute("user_responses", userResponses);
            response.addAttribute("message", "Invoice with id = " + invoiceId + " was handled");
        } catch (SQLException e) {
            e.printStackTrace();
            response.addAttribute("message", "Error: try correct invoiceId");
        }

        return "/home_master";
    }

}