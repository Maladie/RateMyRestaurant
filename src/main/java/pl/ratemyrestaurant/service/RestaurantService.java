package pl.ratemyrestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.dto.RestaurantDTO;

import pl.ratemyrestaurant.dto.RestaurantPIN;

import pl.ratemyrestaurant.model.Ingredient;

import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.repository.RestaurantRepository;
import pl.ratemyrestaurant.service.placesconnectorservice.PlacesConnector;
import pl.ratemyrestaurant.utils.PlaceToRestaurantMapper;
import se.walkercrou.places.Place;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private PlacesConnector placesConnector;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, PlacesConnector placesConnector) {
        this.restaurantRepository = restaurantRepository;
        this.placesConnector = placesConnector;
    }

    public void addRestaurant(RestaurantDTO restaurantDTO) {
        //todo: add body
    }

    public RestaurantDTO getOrRetrieveRestaurantDTOByID(String placeId){
        RestaurantDTO restaurantDTO = getRestaurantDTOById(placeId);
        if(restaurantDTO == null){
            restaurantDTO = retrieveDtoIfNotExistInDB(placeId);
        }
        return restaurantDTO;
    }

    public RestaurantDTO getRestaurantDTOById(String id) {
        return transformRestaurantToDTO(restaurantRepository.findOne(id));
    }

    private RestaurantDTO retrieveDtoIfNotExistInDB(String placeId){
        Place place = placesConnector.retrievePlaceById(placeId);
        Restaurant restaurant = PlaceToRestaurantMapper.mapToRestaurant(place);
        return transformRestaurantToDTO(restaurant);
    }

    private RestaurantDTO transformRestaurantToDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO(restaurant);
        return restaurantDTO;
    }


    public RestaurantPIN getRestaurantPINById(String id) {
        return transformRestaurantToPIN(restaurantRepository.findOne(id));
    }

    private RestaurantPIN transformRestaurantToPIN(Restaurant restaurant) {
        RestaurantPIN restaurantPIN = new RestaurantPIN(restaurant);
        return restaurantPIN;
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
