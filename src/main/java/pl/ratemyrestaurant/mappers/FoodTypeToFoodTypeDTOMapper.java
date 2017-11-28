package pl.ratemyrestaurant.mappers;

import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.model.FoodType;

public class FoodTypeToFoodTypeDTOMapper {

    public static FoodTypeDTO mapFoodTypeToFoodTypeDTO(FoodType foodType) {
        return new FoodTypeDTO(foodType.getName());
    }

    public static FoodType mapFoodTypeDTOToFoodType(FoodTypeDTO foodTypeDTO) {
        return new FoodType(foodTypeDTO.getName());
    }
}
