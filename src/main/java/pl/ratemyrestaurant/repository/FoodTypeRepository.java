package pl.ratemyrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ratemyrestaurant.model.FoodType;

public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {

    FoodType findByName(String name);
}
