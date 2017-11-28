package pl.ratemyrestaurant.dto;

public class FoodTypeDTO {

    private String name;

    public FoodTypeDTO() {}

    public FoodTypeDTO(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
