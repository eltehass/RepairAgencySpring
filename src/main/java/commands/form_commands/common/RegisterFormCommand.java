package commands.form_commands.common;

import model.roles.BasePerson;
import model.roles.Customer;
import model.roles.Manager;
import model.roles.Master;
import org.springframework.ui.Model;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class RegisterFormCommand extends BaseFormCommand {


    public RegisterFormCommand(Map<String, String> request, Model response, Map<String, BasePerson> sessionParams) {
        super(request, response, sessionParams);
    }

    @Override
    public String execute() {
        String userLogin = request.get("signUpLoginInput");
        String userPass = request.get("signUpPasswordInput");
        String userRepeatPass = request.get("signUpRepeatPasswordInput");
        String userName = request.get("signUpName");
        String userSurname = request.get("signUpSurname");
        String userAge = request.get("signUpAge");
        String userEmail = request.get("signUpEmail");
        String userPhone = request.get("signUpPhone");

        if (!userLogin.isEmpty() && !userPass.isEmpty() && !userRepeatPass.isEmpty() && !userName.isEmpty() && !userSurname.isEmpty() && !userAge.isEmpty() && !userEmail.isEmpty() && !userPhone.isEmpty() && userPass.equals(userRepeatPass)) {
            BasePerson user = null;
            try {
                user = dbProvider.personInfoDao().checkIfExist(userLogin, userPass, userName, userSurname);
            } catch (SQLException e) {
                e.printStackTrace();
                response.addAttribute("message", "Incorrect input data while registration");

                return "/login";
            }


            if (user == null) {

                BasePerson newUser = new Customer(userName, userSurname, Integer.parseInt(userAge), userLogin, userPass, userEmail, userPhone);

                try {
                    dbProvider.personInfoDao().create(newUser);
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.addAttribute("message", "Trouble with creating new user, try again with correct data");

                    return "/login";
                }
                response.addAttribute("message", "Success registration, now try to login");

                return "/login";
            } else {
                response.addAttribute("message", "Error: user with such credentials exists");

                return "/login";
            }
        } else {
            response.addAttribute("message", "Incorrect data while registration");

            return "/login";
        }
    }
}
