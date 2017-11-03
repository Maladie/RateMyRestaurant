package pl.ratemyrestaurant.model;

public enum FoodType {

    KEBAB("Kebab"),
    ZAPIEKANKA("Zapiekanka"),
    BURGER("Burger"),
    FRIES("Frytki"),
    PIZZA("Pizza");

   private String polishFoodType;

    FoodType(String polishFoodType) {
        this.polishFoodType = polishFoodType;
    }

    public String getPolishFoodType() {
        return polishFoodType;
    }
}
