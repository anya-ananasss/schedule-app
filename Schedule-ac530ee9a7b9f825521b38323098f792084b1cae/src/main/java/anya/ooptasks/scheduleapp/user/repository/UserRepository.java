package anya.ooptasks.scheduleapp.user.repository;


import anya.ooptasks.scheduleapp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail (String email);

    User findByUsername (String username);
}