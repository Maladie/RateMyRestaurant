package pl.ratemyrestaurant.exception;

public class IngredientRatingNotFoundException extends APIValidationException{
    public IngredientRatingNotFoundException() {
        super();
    }

    public IngredientRatingNotFoundException(String message) {
        super(message);
    }
}
