package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.FoodTypeDTO;
import pl.ratemyrestaurant.exception.NoSuchFoodTypeException;
import pl.ratemyrestaurant.mappers.FoodTypeToFoodTypeDTOMapper;
import pl.ratemyrestaurant.model.FoodType;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.repository.FoodTypeRepository;
import pl.ratemyrestaurant.service.FoodTypeService;
import pl.ratemyrestaurant.type.APIInfoCodes;

import java.util.List;
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
        FoodType foodType = foodTypeRepository.findByName(name);
        if (foodType != null) {
            return FoodTypeToFoodTypeDTOMapper.mapFoodTypeToFoodTypeDTO(foodType);
        }
        Info info = new Info.InfoBuilder().setHttpStatusCode(404).setDescription("No such foodType " + name).setInfoCode(APIInfoCodes.FOOD_TYPE_NOT_FOUND).build();
        throw new NoSuchFoodTypeException(info);
    }

    public FoodType getFoodTypeByFoodTypeDTO(FoodTypeDTO foodTypeDTO) {
        return foodTypeRepository.findByNameIgnoreCase(foodTypeDTO.getName());
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
}
