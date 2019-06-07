package com.leo.coursework;

import commands.FormCommandFactory;
import commands.form_commands.common.BaseFormCommand;
import extra.HPage;
import model.roles.BasePerson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("user")
public class SpringBaseController {

    @RequestMapping(value = "/LoginPage", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPageRequestsHandler(@RequestParam Map<String, String> allRequestParams, Model model) {
        Map<String, BasePerson> sessionParams = new HashMap<>();
        FormCommandFactory factory = new FormCommandFactory(allRequestParams, model, sessionParams);

        if (allRequestParams.get("command") == null || allRequestParams.get("command").isEmpty() || factory.getCommand(allRequestParams.get("command")) == null) {
            return "/login";
        }

        BaseFormCommand command = factory.getCommand(allRequestParams.get("command"));
        String page = command.execute();

        BasePerson user = sessionParams.get("user");

        if (user == null)
            model.addAttribute("user", null);
        else
            model.addAttribute("user", user);

        return page;
    }

    @RequestMapping(value = "/HomePage", method = {RequestMethod.GET, RequestMethod.POST})
    public String homePageRequestsHandler(@SessionAttribute("user") BasePerson user, @RequestParam Map<String, String> allRequestParams, Model model) {
        Map<String, BasePerson> sessionParams = new HashMap<>();
        sessionParams.put("user", user);

        FormCommandFactory factory = new FormCommandFactory(allRequestParams, model, sessionParams);

        if (allRequestParams.get("command") == null || allRequestParams.get("command").isEmpty() || factory.getCommand(allRequestParams.get("command")) == null) {
            return HPage.CURRENT_HOME_PAGE;
        }

        BaseFormCommand command = factory.getCommand(allRequestParams.get("command"));
        String page = command.execute();

        return page;
    }
}