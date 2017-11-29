package pl.ratemyrestaurant.exception;

import pl.ratemyrestaurant.model.Info;

public class NoSuchFoodTypeException extends APIValidationException {
    public NoSuchFoodTypeException() {
        super();
    }

    public NoSuchFoodTypeException(String message) {
        super(message);
    }

    public NoSuchFoodTypeException(Info info) {
        super(info);
    }
}
