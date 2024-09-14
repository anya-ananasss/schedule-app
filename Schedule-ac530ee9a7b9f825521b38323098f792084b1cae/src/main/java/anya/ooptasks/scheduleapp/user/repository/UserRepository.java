package anya.ooptasks.scheduleapp.user.repository;


import anya.ooptasks.scheduleapp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail (String email);

    User findByUsername (String username);

    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    int findIdByUsername (String username);

    User findUserById (int id);
}