package pl.ratemyrestaurant.exception;

class APIException extends RuntimeException {
    APIException() {
        super();
    }

    APIException(String message) {
        super(message);
    }
}
