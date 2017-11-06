package pl.ratemyrestaurant.service.placesconnectorservice;

import pl.ratemyrestaurant.model.UserSearchCircle;
import se.walkercrou.places.Place;

import java.util.Set;

public interface PlacesConnector {
    Set<Place> retrieveRestaurants(UserSearchCircle userSearchCircle)  throws InterruptedException;
    Place retrieveRestaurantById(String placeId);
}
