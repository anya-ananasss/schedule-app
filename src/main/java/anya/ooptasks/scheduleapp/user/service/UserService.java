package anya.ooptasks.scheduleapp.user.service;


import anya.ooptasks.scheduleapp.exceptions.EmailAlreadyExistException;
import anya.ooptasks.scheduleapp.user.model.User;
import anya.ooptasks.scheduleapp.user.repository.UserRepository;
import anya.ooptasks.scheduleapp.exceptions.UsernameAlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public void registerNewUserAccount(User user) throws UsernameAlreadyExistException, EmailAlreadyExistException{
        if (emailExists(user.getEmail())) {
            throw new EmailAlreadyExistException("There is an account with that email address: "
                    + user.getEmail());
        }
        if (nicknameExists(user.getUsername())) {
            throw new UsernameAlreadyExistException("There is an account with that nickname"
                    + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthority("ROLE_USER");
        repository.save(user);
    }

    private boolean emailExists(String email) {
        return repository.findByEmail(email) != null;
    }

    private boolean nicknameExists(String username) {
        return repository.findByUsername(username) != null;
    }

    public int findIdByUsername (String username) { return repository.findIdByUsername(username);}

    public User findUserById (int id) {return  repository.findUserById(id);}
}