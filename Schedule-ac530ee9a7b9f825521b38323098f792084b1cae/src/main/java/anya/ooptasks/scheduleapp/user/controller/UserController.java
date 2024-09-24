package anya.ooptasks.scheduleapp.user.controller;

import anya.ooptasks.scheduleapp.exceptions.EmailAlreadyExistException;
import anya.ooptasks.scheduleapp.exceptions.UsernameAlreadyExistException;
import anya.ooptasks.scheduleapp.schedule.service.ScheduleService;
import anya.ooptasks.scheduleapp.user.model.User;
import anya.ooptasks.scheduleapp.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ScheduleService scheduleService;
    @GetMapping("/registration")
    public String showRegistrationForm(Model model,  @RequestParam(required = false) boolean error) {
        if (error){
            model.addAttribute("stateMessage", "Не удалось зарегистрироваться!");
        }
        return "registration";
    }
    @GetMapping("/login")
    public String showLoginForm(Model model,  @RequestParam(required = false) boolean error) {
        if (error){
            model.addAttribute("stateMessage", "Не удалось войти! Проверьте корректность введенных данных");
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginUser () {
            return "login";
    }

    @ResponseBody
    @PostMapping("/registration")
    public RedirectView registerUserAccount(@RequestBody User user){
        try {
            userService.registerNewUserAccount(user);
            scheduleService.createDefaultSchedule(user);
            return new RedirectView("/login");
        } catch (UsernameAlreadyExistException | EmailAlreadyExistException usernameEx){
            return new RedirectView("/registration");
        }
    }
}

