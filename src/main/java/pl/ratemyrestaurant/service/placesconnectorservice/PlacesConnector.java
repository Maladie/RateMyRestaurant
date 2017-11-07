package pl.ratemyrestaurant.service.placesconnectorservice;

import org.springframework.stereotype.Service;
import pl.ratemyrestaurant.model.UserSearchCircle;
import se.walkercrou.places.Place;

import java.util.Set;

@Service
public interface PlacesConnector {
    Set<Place> retrievePlaces(UserSearchCircle userSearchCircle)  throws InterruptedException;
    Place retrievePlaceById(String placeId);
}
