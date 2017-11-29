package pl.ratemyrestaurant.exception;

import pl.ratemyrestaurant.model.Info;

public class APIValidationException extends APIException{
    private Info info;
    public APIValidationException() {
        super();
        info = new Info();
    }
    public APIValidationException(String message) {
        super(message);
        info = new Info();
        info.setDesc(message);
    }

    public APIValidationException(Info info) {
        super(info.getDesc());
        this.info = info;
    }

    public Info getInfo() {
        return info;
    }
}
