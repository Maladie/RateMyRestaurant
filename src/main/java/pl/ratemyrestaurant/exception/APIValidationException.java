package pl.ratemyrestaurant.exception;

public class APIValidationException extends APIException{

    public APIValidationException() {
        super();
    }
    public APIValidationException(String message) {
        super(message);
    }
}
