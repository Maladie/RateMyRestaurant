package pl.ratemyrestaurant.dto;

import pl.ratemyrestaurant.model.FoodType;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Location;
import pl.ratemyrestaurant.model.Rating;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class RestaurantDTO {

    private String id;
    private String name;
    private Location location;
    private Set<FoodType> foodTypes;
    private boolean newlyCreated;
    private Set<Rating> ingredientRatings;

    public RestaurantDTO() {}

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Set<FoodType> getFoodTypes() {
        return foodTypes;
    }

    public boolean isNewlyCreated() {
        return newlyCreated;
    }

    public void setNewlyCreated(boolean newlyCreated) {
        this.newlyCreated = newlyCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setFoodTypes(Set<FoodType> foodTypes) {
        this.foodTypes = foodTypes;
    }

    public void setIngredientRatings(Set<Rating> ingredientRatings) {
        this.ingredientRatings = ingredientRatings;
    }

    public Set<Rating> getIngredientRatings() {
        return ingredientRatings;
    }

//    public IngredientDTO getSpecificIngredient(String name){
//        Optional<Ingredient> ingr = ingredients.stream().filter(i -> i.getName().equals(name)).findFirst();
//        if(ingr.isPresent()){
//            return ingr.get().toIngredientDto();
//        }
//        return null;
//    }

}
