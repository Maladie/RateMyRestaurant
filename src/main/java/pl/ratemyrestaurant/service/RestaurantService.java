package pl.ratemyrestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.model.Ingredient;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.repository.RestaurantRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public void addRestaurant(RestaurantDTO restaurantDTO) {
        //todo: add body
    }

    public RestaurantDTO getOrRetrieveRestautantDTOByID(String placeId){
        RestaurantDTO restaurantDTO = getRestaurantDTOById(placeId);
        if(restaurantDTO == null){
            //todo
        }
        return restaurantDTO;
    }

    public RestaurantDTO getRestaurantDTOById(String id) {
        return transformRestaurantToDTO(restaurantRepository.findOne(id));
    }

    private RestaurantDTO transformRestaurantToDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO(restaurant);
        return restaurantDTO;
    }

    public List<IngredientDTO> getIngredientsByThumbs(String restaurantId, String orderBy) {
        Set<Ingredient> ingredients = getRestaurantDTOById(restaurantId).getIngredients();
        List<Ingredient> ingredientList = new ArrayList<>(ingredients);
        if("name".equals(orderBy)){
            Collections.sort(ingredientList, new Comparator<Ingredient>() {
                @Override
                public int compare(Ingredient o1, Ingredient o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
        Collections.sort(ingredientList);
        return ingredientList.stream().map(i -> i.toIngredientDto()).collect(Collectors.toList());

    }

}
