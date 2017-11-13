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
        List<FoodTypeDTO> allFoodTypesDTO = allFoodTypes.stream().map(FoodTypeToFoodTypeDTOMapper::mapFoodTypeToFoodTypeDTO).collect(Collectors.toList());
        return allFoodTypesDTO;
    }

    @Override
    public FoodTypeDTO getFoodTypeDTOByName(String name) {
        FoodType foodType = foodTypeRepository.findByName(name);
        if(foodType != null) {
            return FoodTypeToFoodTypeDTOMapper.mapFoodTypeToFoodTypeDTO(foodType);
        }
        throw new NoSuchElementException("No such foodType");
    }

    @Override
    public FoodType addNewFoodType(FoodTypeDTO foodTypeDTO) {
        FoodType foodType;
        if (!getAllFodTypes().stream().filter(ft -> ft.getName().equalsIgnoreCase(foodTypeDTO.getName())).findFirst().isPresent()) {
            foodType = FoodTypeToFoodTypeDTOMapper.mapFoodTypeDTOToFoodType(foodTypeDTO);
            foodTypeRepository.save(foodType);
        } else {
            foodType = foodTypeRepository.findByName(foodTypeDTO.getName());
        }
        return foodType;
    }

    private List<FoodType> getAllFodTypes() {
        return foodTypeRepository.findAll();
    }
}
