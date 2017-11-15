package pl.ratemyrestaurant.model;


import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Restaurant {

    @Id
    private String id;
    private String name;
    @Embedded
    private Location location;
    @ManyToMany
    private Set<FoodType> foodTypes;

    public Restaurant() {}

    public Restaurant(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Restaurant(String id, String name, Location location, Set<FoodType> foodTypes) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.foodTypes = foodTypes;
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
}
