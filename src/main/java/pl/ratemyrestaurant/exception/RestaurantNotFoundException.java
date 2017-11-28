package pl.ratemyrestaurant.exception;

public class RestaurantNotFoundException extends APIValidationException{

    public RestaurantNotFoundException(){
        super();
    }

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
