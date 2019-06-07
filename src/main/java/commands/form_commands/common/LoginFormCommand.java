package commands.form_commands.common;

import extra.HPage;
import model.extra.Application;
import model.extra.Invoice;
import model.extra.RegectedApplication;
import model.extra.UserResponse;
import model.roles.BasePerson;
import model.roles.PersonType;
import org.springframework.ui.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginFormCommand extends BaseFormCommand {


    public LoginFormCommand(Map<String, String> request, Model response, Map<String, BasePerson> sessionParams) {
        super(request, response, sessionParams);
    }

    @Override
    public String execute() {
        String userLogin = request.get("signInLoginInput");
        String userPass = request.get("signInPasswordInput");

        if (!userLogin.isEmpty() && !userPass.isEmpty()) {
            BasePerson user = null;
            try {
                user = dbProvider.personInfoDao().logIn(userLogin, userPass);
            } catch (SQLException e) {
                e.printStackTrace();
                response.addAttribute("message", "Incorrect login and password");
                return "/login";
            }

            if (user != null) {
                response.addAttribute("usrName", user.name);
                response.addAttribute("usrSurname", user.surname);

                PersonType userType = null;
                try {
                    userType = dbProvider.personTypeDao().getEntityById(user.idPersonType);
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.addAttribute("message", "Incorrect UserType");
                    return "/login";

                }

                String userPageLink = "";
                switch (userType) {
                    case CUSTOMER: {
                        userPageLink = "/home_customer";

                        List<Invoice> invoices = new ArrayList<>();
                        List<RegectedApplication> regectedApplications = new ArrayList<>();

                        try {
                            invoices = dbProvider.invoiceDao().getFinishedForCustomer(user.id);
                            regectedApplications = dbProvider.regectedApplicationsDao().getAllForCustomer(user.id);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        response.addAttribute("finished_invoices", invoices);
                        response.addAttribute("rejected_applications", regectedApplications);

                        break;
                    }

                    case MANAGER: {
                        userPageLink = "/home_manager";

                        List<Application> applications = new ArrayList<>();
                        List<BasePerson> masters = new ArrayList<>();

                        try {
                            applications = dbProvider.applicationDao().getAllNotHandled();
                            masters = dbProvider.personInfoDao().getAllMasters();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        response.addAttribute("unhandled_applications", applications);
                        response.addAttribute("masters", masters);

                        break;
                    }

                    case MASTER: {
                        userPageLink = "/home_master";

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

                        break;
                    }
                }


                HPage.CURRENT_HOME_PAGE = userPageLink;

                sessionParams.put("user", user);
                System.out.print("USER ID = " + user.id);

                return userPageLink;
            } else {
                response.addAttribute("message", "Incorrect login and password");
                return "/login";

            }
        } else {
            response.addAttribute("message", "One or two fields are empty");
            return "/login";
        }
    }
}