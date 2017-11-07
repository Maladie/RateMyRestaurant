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

    public Restaurant() {}

    public Restaurant(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Restaurant(String id, String name, Location location, Set<FoodType> foodTypes, Set<Ingredient> ingredients) {
        this.id = id;
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

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
