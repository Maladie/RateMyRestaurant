package pl.ratemyrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ratemyrestaurant.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameIgnoreCase(String login);
}
