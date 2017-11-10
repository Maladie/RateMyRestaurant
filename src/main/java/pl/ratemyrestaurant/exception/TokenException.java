package pl.ratemyrestaurant.exception;

import java.util.UUID;

public class TokenException extends Exception {

    private final String exceptionId;
    private final String token;
    private final String description;
    private final Throwable t;

    public TokenException(String description){
        super(description);
        this.description = description;
        this.exceptionId = UUID.randomUUID().toString();
        this.t = null;
        this.token = null;
    }

    public TokenException(String description, Throwable t){
        super(description, t);
        this.description = description;
        this.exceptionId = UUID.randomUUID().toString();
        this.t = t;
        this.token = null;
    }

    public TokenException(String token, String description, Throwable t){
        super(description, t);
        this.description = description;
        this.exceptionId = UUID.randomUUID().toString();
        this.t = t;
        this.token = token;
    }

    public String getExceptionId() {
        return exceptionId;
    }
    public String getToken() {
        return token;
    }
    public String getDescription() {
        return description;
    }
    public Throwable getT() {
        return t;
    }

    @Override
    public String toString() {
        return "TokenException [token=" + token + ", description=" + description + "]";
    }

}

