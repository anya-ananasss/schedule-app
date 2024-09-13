package anya.ooptasks.scheduleapp.user.service;


import anya.ooptasks.scheduleapp.user.model.User;
import anya.ooptasks.scheduleapp.user.repository.UserRepository;
import anya.ooptasks.scheduleapp.exceptions.UserAlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public void registerNewUserAccount(User user) throws UserAlreadyExistException {
        System.out.println("aaaaa");
        if (emailExists(user.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + user.getEmail());
        }
        if (nicknameExists(user.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that nickname"
                    + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority("ROLE_USER");
        System.out.println(user.getPassword());
        repository.save(user);
    }

    private boolean emailExists(String email) {
        return repository.findByEmail(email) != null;
    }

    private boolean nicknameExists(String username) {
        return repository.findByUsername(username) != null;
    }
}