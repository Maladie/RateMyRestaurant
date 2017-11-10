package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.model.UserSearchCircle;
import se.walkercrou.places.Place;

import java.util.Set;

public interface PlacesConnector {
    Set<Place> retrievePlaces(UserSearchCircle userSearchCircle)  throws InterruptedException;
    Place retrievePlaceById(String placeId);
}
