package pl.ratemyrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ratemyrestaurant.model.FoodType;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    List<Restaurant> findByFoodTypesIn(List<FoodType> foodTypes);
    List<Restaurant> findByIngredientsIn(List<Ingredient> ingredients);
    Restaurant findByName(String name);
}
