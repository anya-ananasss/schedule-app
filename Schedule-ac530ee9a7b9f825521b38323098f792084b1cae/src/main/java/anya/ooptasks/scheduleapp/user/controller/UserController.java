package anya.ooptasks.scheduleapp.user.controller;

import anya.ooptasks.scheduleapp.exceptions.EmailAlreadyExistException;
import anya.ooptasks.scheduleapp.exceptions.UsernameAlreadyExistException;
import anya.ooptasks.scheduleapp.schedule.service.ScheduleService;
import anya.ooptasks.scheduleapp.user.model.User;
import anya.ooptasks.scheduleapp.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ScheduleService scheduleService;
    @GetMapping("/registration")
    public String showRegistrationForm() {
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
    public ResponseEntity<String> registerUserAccount(@RequestBody User user){
        try {
            userService.registerNewUserAccount(user);
            scheduleService.createDefaultSchedule(user);
            return ResponseEntity.ok("Успешно");
        } catch (UsernameAlreadyExistException usernameEx){
            return ResponseEntity.badRequest().body("Пользователь с таким именем уже существует!");
        } catch (EmailAlreadyExistException emailEx) {
            return ResponseEntity.badRequest().body("Пользователь с таким адресом почты уже зарегистрирован!");
        }
    }
}

