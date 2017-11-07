package pl.ratemyrestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.IngredientDTO;
import pl.ratemyrestaurant.dto.RestaurantDTO;

import pl.ratemyrestaurant.dto.RestaurantPIN;

import pl.ratemyrestaurant.mappers.RestaurantToPinMapper;
import pl.ratemyrestaurant.mappers.RestaurantToRestaurantDTOMapper;
import pl.ratemyrestaurant.model.Ingredient;

import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.repository.RestaurantRepository;
import pl.ratemyrestaurant.service.placesconnectorservice.PlacesConnector;
import pl.ratemyrestaurant.mappers.PlaceToRestaurantMapper;
import se.walkercrou.places.Place;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import java.util.*;
import java.util.stream.Collectors;

import static pl.ratemyrestaurant.mappers.RestaurantToPinMapper.*;
import static pl.ratemyrestaurant.mappers.RestaurantToRestaurantDTOMapper.*;

@Service
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private PlacesConnector placesConnector;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, PlacesConnector placesConnector) {
        this.restaurantRepository = restaurantRepository;
        this.placesConnector = placesConnector;
    }

    public void addOrUpdateRestaurant(RestaurantDTO restaurantDTO) {
        if(restaurantDTO.isNewlyCreated()){
           addNewRestaurant(restaurantDTO);
        }else {
            updateRestaurant(restaurantDTO);
        }
    }

    private void addNewRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = mapToRestaurant(restaurantDTO);
        restaurantRepository.save(restaurant);
    }

    private void updateRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.getOne(restaurantDTO.getId());
        restaurant.setFoodTypes(restaurantDTO.getFoodTypes());
        restaurant.setIngredients(restaurantDTO.getIngredients());
        restaurantRepository.save(restaurant);
    }

    public RestaurantDTO getOrRetrieveRestaurantDTOByID(String placeId){
        RestaurantDTO restaurantDTO = getRestaurantDTOById(placeId);
        if (restaurantDTO == null) {
            restaurantDTO = retrieveDtoIfNotExistInDB(placeId);
        } else {
            restaurantDTO.setNewlyCreated(false);
        }
        return restaurantDTO;
    }

    public RestaurantDTO getRestaurantDTOById(String id) {
        return transformRestaurantToDTO(restaurantRepository.findOne(id));
    }

    private RestaurantDTO retrieveDtoIfNotExistInDB(String placeId) {
        Place place = placesConnector.retrievePlaceById(placeId);
        Restaurant restaurant = PlaceToRestaurantMapper.mapToRestaurant(place);
        RestaurantDTO restaurantDTO = transformRestaurantToDTO(restaurant);
        restaurantDTO.setNewlyCreated(true);
        return restaurantDTO;
    }

    private RestaurantDTO transformRestaurantToDTO(Restaurant restaurant) {
        return mapToRestaurantDto(restaurant);
    }

    public RestaurantPIN getRestaurantPINById(String id) {
        return transformRestaurantToPIN(restaurantRepository.findOne(id));
    }

    private RestaurantPIN transformRestaurantToPIN(Restaurant restaurant) {
        return mapRestaurantToPin(restaurant);
    }

    public List<IngredientDTO> getIngredientsByThumbs(String restaurantId, String orderBy) {
        Set<Ingredient> ingredients = getRestaurantDTOById(restaurantId).getIngredients();
        List<Ingredient> ingredientList = new ArrayList<>(ingredients);
        if ("name".equals(orderBy)) {
            Collections.sort(ingredientList, Comparator.comparing(Ingredient::getName));
        } else {
            Collections.sort(ingredientList);
        }
        return ingredientList.stream().map(i -> i.toIngredientDto()).collect(Collectors.toList());
    }


    public List<RestaurantDTO> getRestaurantsContainingIngredient(String name) {
        List<String> restaurantIds = entityManager.createStoredProcedureQuery("restaurant_by_ingredient")
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .setParameter(1, name).getResultList();
        List<RestaurantDTO> foundRestaurants = new ArrayList<>();
        restaurantRepository.findByIdIn(restaurantIds).forEach(r -> foundRestaurants.add(transformRestaurantToDTO(r)));
        return foundRestaurants;
    }
}
