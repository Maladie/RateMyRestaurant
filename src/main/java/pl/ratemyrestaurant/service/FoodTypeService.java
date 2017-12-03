package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.exception.NoSuchFoodTypeException;
import pl.ratemyrestaurant.model.FoodType;

import java.util.List;

public interface FoodTypeService {

    List<FoodTypeDTO> getAllFoodTypesDTO();
    FoodTypeDTO getFoodTypeDTOByName(String name) throws NoSuchFoodTypeException;
    FoodTypeDTO addNewFoodType(FoodTypeDTO foodTypeDTO);
    FoodType getFoodTypeByFoodTypeDTO(FoodTypeDTO foodTypeDTO);
}
