package commands.form_commands.customer;

import commands.form_commands.common.BaseFormCommand;
import commands.person_commands.customer.CommentCommand;
import model.extra.Invoice;
import model.extra.RegectedApplication;
import model.roles.BasePerson;
import model.roles.Customer;
import org.springframework.ui.Model;

import javax.servlet.RequestDispatcher;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommentFormCommand extends BaseFormCommand {


    public CommentFormCommand(Map<String, String> request, Model response, Map<String, BasePerson> sessionParams) {
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


        String invoiceStr = request.get("customerInputInvoiceId");
        String comment = request.get("customerInputComment");

        if (!comment.isEmpty() && invoiceStr != null && !invoiceStr.isEmpty()) {
            int invoiceId = Integer.parseInt(invoiceStr);
            if (!invoices.stream().map(invoice -> invoice.id).collect(Collectors.toList()).contains(invoiceId)) {
                response.addAttribute("message", "Write finished invoiceId with your appId, because invoice with id = " + invoiceId + " isn't finished, not yours or doesn't exists");

                return "/home_customer";
            }

            userCommand = new CommentCommand(user, invoiceId, comment);
            try {
                userCommand.execute();
                response.addAttribute("message", "Invoice with id = " + invoiceId + " got comment: " + comment);
            } catch (SQLException e) {
                e.printStackTrace();
                response.addAttribute("message", "Error: try correct invoiceId");
            }
        } else {
            response.addAttribute("message", "Incorrect input data for Comment action");
        }

        return "/home_customer";
    }

}