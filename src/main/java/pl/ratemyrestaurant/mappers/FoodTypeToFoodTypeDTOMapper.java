package pl.ratemyrestaurant.mappers;

import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.model.FoodType;

public class FoodTypeToFoodTypeDTOMapper {

    public static FoodTypeDTO mapFoodTypeToFoodTypeDTO(FoodType foodType) {
        FoodTypeDTO foodTypeDTO = new FoodTypeDTO(foodType.getId(), foodType.getName());
        return foodTypeDTO;
    }

    public static FoodType mapFoodTypeDTOToFoodType(FoodTypeDTO foodTypeDTO) {
        FoodType foodType = new FoodType(foodTypeDTO.getName());
        return foodType;
    }
}
