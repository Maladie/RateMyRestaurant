package pl.ratemyrestaurant.model;

import javax.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Restaurant restaurant;
    @ManyToOne
    private Ingredient ingredient;
    @Embedded
    private Thumb thumb;

    public Rating() {}

    public Rating(Long id, Restaurant restaurant, Ingredient ingredient, Thumb thumb) {
        this.id = id;
        this.restaurant = restaurant;
        this.ingredient = ingredient;
        this.thumb = thumb;
    }

    public Long getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }
}
