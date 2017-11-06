package pl.ratemyrestaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.dto.RestaurantDTO;
import pl.ratemyrestaurant.model.Restaurant;
import pl.ratemyrestaurant.repository.RestaurantRepository;
import pl.ratemyrestaurant.service.placesconnectorservice.PlacesConnector;
import pl.ratemyrestaurant.utils.PlaceToRestaurantMapper;
import se.walkercrou.places.Place;

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
            Place place = placesConnector.retrievePlaceById(placeId);
            Restaurant restaurant = PlaceToRestaurantMapper.mapToRestaurant(place);
            restaurantDTO = transformRestaurantToDTO(restaurant);
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

}
