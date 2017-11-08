package pl.ratemyrestaurant.dto;

import pl.ratemyrestaurant.model.FoodType;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Location;
import pl.ratemyrestaurant.model.Restaurant;

import java.util.Set;

public class RestaurantDTO {

    private String id;
    private String name;
    private Location location;
    private Set<FoodType> foodTypes;
    private Set<Ingredient> ingredients;
    private boolean newlyCreated;

    public RestaurantDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.location = restaurant.getLocation();
        this.foodTypes = restaurant.getFoodTypes();
        this.ingredients = restaurant.getIngredients();

    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Set<FoodType> getFoodTypes() {
        return foodTypes;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean isNewlyCreated() {
        return newlyCreated;
    }

    public void setNewlyCreated(boolean newlyCreated) {
        this.newlyCreated = newlyCreated;
    }
}
