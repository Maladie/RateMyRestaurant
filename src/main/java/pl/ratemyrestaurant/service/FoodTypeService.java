package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.dto.FoodTypeDTO;

import java.util.List;

public interface FoodTypeService {

    List<FoodTypeDTO> getAllFoodTypesDTO();
    FoodTypeDTO getFoodTypeDTOByName(String name);
    FoodTypeDTO addNewFoodType(FoodTypeDTO foodTypeDTO);
}
