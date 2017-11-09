package pl.ratemyrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ratemyrestaurant.model.Rating;

import java.util.Set;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Set<Rating> findByRestaurant_Id(String restaurantId);
}
