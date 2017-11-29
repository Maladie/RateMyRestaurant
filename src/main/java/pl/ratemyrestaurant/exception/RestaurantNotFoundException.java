package pl.ratemyrestaurant.exception;

public class RestaurantNotFoundException extends APIValidationException{

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
