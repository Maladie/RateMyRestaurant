package pl.ratemyrestaurant.dto;

public class FoodTypeDTO {

    private Long id;
    private String name;

    public FoodTypeDTO() {}

    public FoodTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
