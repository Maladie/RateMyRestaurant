package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.exception.APIValidationException;
import pl.ratemyrestaurant.mappers.IngredientToIngredientDTOMapper;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.repository.IngredientRepository;
import pl.ratemyrestaurant.service.IngredientService;
import pl.ratemyrestaurant.type.APIInfoCodes;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.ratemyrestaurant.utils.CheckingUtils.isNullOrEmpty;

@Transactional
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientDTO addIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = null;
        if (!isNullOrEmpty(ingredientDTO.getName())) {
            boolean ingredientNameExists = ingredientRepository.existsByName(ingredientDTO.getName()) != null;//findAll().stream().noneMatch(i -> i.getName().equalsIgnoreCase(ingredientDTO.getName()));
            if (ingredientNameExists) {
                Info info = new Info();
                info.setDesc("Ingredient name already exist in DB.");
                info.setHttpStatusCode(422L);
                info.setInfoCode(APIInfoCodes.INGREDIENT_ALREADY_EXISTS);
                info.setObject(ingredientDTO);
                throw new APIValidationException(info);
            }
            ingredient = new Ingredient();
            ingredient.setName(ingredientDTO.getName());
            ingredientRepository.save(ingredient);
        } else {
            Info info = new Info();
            info.setDesc("Ingredient name invalid. Check object");
            info.setHttpStatusCode(400L);
            info.setInfoCode(APIInfoCodes.INVALID_INGREDIENT_DATA);
            info.setObject(ingredientDTO);
            throw new APIValidationException(info);
        }
        return IngredientToIngredientDTOMapper.mapIngredientToIngredientDTO(ingredient);
    }

    @Override
    public Set<IngredientDTO> getAllIngredientsDTO() {
        List<Ingredient> allIngredients = ingredientRepository.findAll();
        return allIngredients
                .stream()
                .map(IngredientToIngredientDTOMapper::mapIngredientToIngredientDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public IngredientDTO getIngredientDTOById(Long id) {
        Ingredient ingredient = ingredientRepository.findOne(id);
        return ingredient != null ? IngredientToIngredientDTOMapper.mapIngredientToIngredientDTO(ingredient) : null;
    }

}
