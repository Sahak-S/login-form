package com.myproject.loginform.controller;

import com.myproject.loginform.model.UsersModel;
import com.myproject.loginform.services.UsersServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    public final UsersServices usersServices;

    public UsersController(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel) {
        System.out.println("գրանցման հարցում" + usersModel);
        UsersModel registeredUser = usersServices.registerUser(usersModel.getLogin(), usersModel.getPassword(), usersModel.getEmail());
        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel,Model model) {
        System.out.println("մուտքի հարցում" + usersModel);
        UsersModel authenticate = usersServices.authenticate(usersModel.getLogin(), usersModel.getPassword());
        if (authenticate != null) {
            model.addAttribute("userLogin",authenticate.getLogin());
            return "personal_page";
        }else {
            return "error_page";
        }
    }
}
