package pl.ratemyrestaurant.mappers;

import pl.ratemyrestaurant.model.Location;
import pl.ratemyrestaurant.model.Restaurant;
import se.walkercrou.places.Place;

public class PlaceToRestaurantMapper {

    public static Restaurant mapToRestaurant(Place place){
        return new Restaurant(
                place.getPlaceId()
                , place.getName()
                , new Location(place.getLatitude()
                , place.getLongitude()));
    }
}
