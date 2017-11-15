package pl.ratemyrestaurant.model;

public class Vote {

    private String restaurantId;
    private Long ingredientId;
    private boolean good;

    public Vote() {}

    public Vote(String restaurantId, Long ingredientId, boolean good) {
        this.restaurantId = restaurantId;
        this.ingredientId = ingredientId;
        this.good = good;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }


}
