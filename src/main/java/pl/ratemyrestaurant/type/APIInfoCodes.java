package pl.ratemyrestaurant.type;

public enum APIInfoCodes {
    OK(0L),
    ENTITY_ALREADY_EXISTS(422001L),
    RESTAURANT_NOT_EXISTS(422002L),
    INGREDIENT_RATING_NOT_EXISTS(422003L),
    ENTITY_NOT_FOUND(404001L),
    //auth
    INVALID_USERNAME(400001L),
    INVALID_PASSWORD(400002L),
    USERNAME_ALREADY_USED(400003L),
    USERNAME_NOT_FOUND(400004L),
    WRONG_USER_PASSWORD(400005L);

    private long value;

    APIInfoCodes(Long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
