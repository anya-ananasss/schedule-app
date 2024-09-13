package anya.ooptasks.scheduleapp.user.controller;

import anya.ooptasks.scheduleapp.exceptions.UserAlreadyExistException;
import anya.ooptasks.scheduleapp.user.model.User;
import anya.ooptasks.scheduleapp.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {

        return "registration";
    }
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser (Authentication authentication, HttpSession session, Model model) {
        if (authentication != null) {
            User user = (User) authentication.getPrincipal(); // Получаем информацию о пользователе из Authentication
            session.setAttribute("user", user); // Сохраняем информацию о пользователе в сессию
            return "redirect:/schedule"; // Перенаправляем на страницу /homepage
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Перенаправляем обратно на страницу входа
        }
    }
    
    @ResponseBody
    @PostMapping("/registration")
    public ResponseEntity<String> registerUserAccount(@RequestBody User user){
        System.out.println("я не ом");
        try {
            userService.registerNewUserAccount(user);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (UserAlreadyExistException userEx) {
           return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
