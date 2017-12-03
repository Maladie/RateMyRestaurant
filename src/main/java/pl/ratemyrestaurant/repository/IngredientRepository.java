package pl.ratemyrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ratemyrestaurant.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient findByName(String name);
    Boolean existsByName(String name);
}
