package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.dto.RestaurantPIN;
import pl.ratemyrestaurant.mappers.PlaceToRestaurantMapper;
import pl.ratemyrestaurant.mappers.RestaurantToPinMapper;
import pl.ratemyrestaurant.mappers.RestaurantToRestaurantDTOMapper;
import pl.ratemyrestaurant.model.Rating;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.model.UserSearchCircle;
import pl.ratemyrestaurant.repository.RestaurantRepository;
import pl.ratemyrestaurant.service.PlacesConnector;
import pl.ratemyrestaurant.service.RestaurantService;
import se.walkercrou.places.Place;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.ratemyrestaurant.mappers.RestaurantToPinMapper.mapRestaurantToPin;
import static pl.ratemyrestaurant.mappers.RestaurantToRestaurantDTOMapper.mapToRestaurant;
import static pl.ratemyrestaurant.mappers.RestaurantToRestaurantDTOMapper.mapToRestaurantDto;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;
    private PlacesConnector placesConnector;
    private RatingServiceImpl ratingServiceImpl;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, PlacesConnector placesConnector
            , RatingServiceImpl ratingServiceImpl) {
        this.restaurantRepository = restaurantRepository;
        this.placesConnector = placesConnector;
        this.ratingServiceImpl = ratingServiceImpl;
    }

    public Set<RestaurantPIN> retrieveRestaurantsInRadius(UserSearchCircle userSearchCircle) {
        Set<Place> places = new HashSet<>();
        Set<RestaurantPIN> restaurantPINs = new HashSet<>();
        try {
            places = placesConnector.retrievePlaces(userSearchCircle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        places.forEach(place -> {
            RestaurantPIN restaurantPIN = mapPlaceToRestaurantDto(place);
            restaurantPINs.add(restaurantPIN);
        });
        return restaurantPINs;
    }

    public RestaurantPIN mapPlaceToRestaurantDto(Place place){
        Restaurant restaurant = PlaceToRestaurantMapper.mapToRestaurant(place);
        RestaurantPIN restaurantPIN = RestaurantToPinMapper.mapRestaurantToPin(restaurant);
        return restaurantPIN;
    }

    public void addOrUpdateRestaurant(RestaurantDTO restaurantDTO) {
        if (restaurantDTO.isNewlyCreated()) {
            addNewRestaurant(restaurantDTO);
        } else {
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
        restaurantRepository.save(restaurant);
    }

    public RestaurantDTO getOrRetrieveRestaurantDTOByID(String placeId) {
        RestaurantDTO restaurantDTO = getRestaurantDTOById(placeId);
        if (restaurantDTO == null) {
            restaurantDTO = retrieveDtoIfNotExistInDB(placeId);
        } else {
            restaurantDTO.setNewlyCreated(false);
        }
        return restaurantDTO;
    }

    public RestaurantDTO getRestaurantDTOById(String id) {
        Restaurant restaurant = restaurantRepository.findOne(id);
        if(restaurant == null){
            return null;
        }
        return transformRestaurantToDTO(restaurant);
    }

    private RestaurantDTO retrieveDtoIfNotExistInDB(String placeId) {
        Place place = placesConnector.retrievePlaceById(placeId);
        Restaurant restaurant = PlaceToRestaurantMapper.mapToRestaurant(place);
        RestaurantDTO restaurantDTO = transformRestaurantToDTO(restaurant);
        restaurantDTO.setNewlyCreated(true);
        return restaurantDTO;
    }

    private RestaurantDTO transformRestaurantToDTO(Restaurant restaurant) {
        String restaurantId = restaurant.getId();
        Set<Rating> ratings = ratingServiceImpl.retrieveRestaurantRatings(restaurantId);
        return mapToRestaurantDto(restaurant, ratings);
    }

    public RestaurantPIN getRestaurantPINById(String id) {
        return transformRestaurantToPIN(restaurantRepository.findOne(id));
    }

    @Override
    public Set<RestaurantDTO> getRestaurantsDTOByFoodType(List<String> foodType) {
        List<Restaurant> restaurantsByFoodType = restaurantRepository.findByFoodTypesIn(foodType);
        Set<RestaurantDTO> restaurantsDTOByFoodType = restaurantsByFoodType.stream()
                .map(i -> RestaurantToRestaurantDTOMapper.mapToRestaurantDto(i, getRestaurantRatings(i.getId()))).collect(Collectors.toSet());
        return restaurantsDTOByFoodType;
    }

    private RestaurantPIN transformRestaurantToPIN(Restaurant restaurant) {
        return mapRestaurantToPin(restaurant);
    }

    private Set<Rating> getRestaurantRatings(String restaurantId) {
        Set<Rating> restaurantRatings = ratingServiceImpl.retrieveRestaurantRatings(restaurantId);
        return restaurantRatings;
    }
}
