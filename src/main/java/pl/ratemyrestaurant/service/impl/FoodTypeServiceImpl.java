package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.mappers.FoodTypeToFoodTypeDTOMapper;
import pl.ratemyrestaurant.model.FoodType;
import pl.ratemyrestaurant.repository.FoodTypeRepository;
import pl.ratemyrestaurant.service.FoodTypeService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FoodTypeServiceImpl implements FoodTypeService {

    private FoodTypeRepository foodTypeRepository;

    @Autowired
    public FoodTypeServiceImpl(FoodTypeRepository foodTypeRepository) {
        this.foodTypeRepository = foodTypeRepository;
    }

    @Override
    public List<FoodTypeDTO> getAllFoodTypesDTO() {
        List<FoodType> allFoodTypes = foodTypeRepository.findAll();
        return allFoodTypes.stream().map(FoodTypeToFoodTypeDTOMapper::mapFoodTypeToFoodTypeDTO).collect(Collectors.toList());
    }

    @Override
    public FoodTypeDTO getFoodTypeDTOByName(String name) {
        FoodType foodType = foodTypeRepository.findByNameIgnoreCase(name);
        if (foodType != null) {
            return FoodTypeToFoodTypeDTOMapper.mapFoodTypeToFoodTypeDTO(foodType);
        }
        throw new NoSuchElementException("No such foodType " + name);
    }

    @Override
    public FoodTypeDTO addNewFoodType(FoodTypeDTO foodTypeDTO) {
        FoodType foodType = foodTypeRepository.findByNameIgnoreCase(foodTypeDTO.getName());
        //TODO simplify
        if (foodType == null) {
            foodType = FoodTypeToFoodTypeDTOMapper.mapFoodTypeDTOToFoodType(foodTypeDTO);
            foodTypeRepository.save(foodType);
            return FoodTypeToFoodTypeDTOMapper.mapFoodTypeToFoodTypeDTO(foodType);
        }
        return null;
    }

    private List<FoodType> getAllFodTypes() {
        return foodTypeRepository.findAll();
    }
}
