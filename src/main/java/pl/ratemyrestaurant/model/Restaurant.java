package pl.ratemyrestaurant.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Restaurant {

    @Id
    private String id;
    private String name;
    @Embedded
    private Location location;
    @ElementCollection
    private Set<FoodType> foodTypes;
    @ManyToMany
    private Set<Ingredient> ingredients;

    Restaurant() {}

    public Restaurant(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public Restaurant(String name, Location location, Set<FoodType> foodTypes, Set<Ingredient> ingredients) {
        this.name = name;
        this.location = location;
        this.foodTypes = foodTypes;
        this.ingredients = ingredients;
    }

    public String getId() {
        return id;
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
}
