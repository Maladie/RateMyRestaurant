package pl.ratemyrestaurant.config;

public class Constants {

    public static final int CACHE_TIMEOUT_IN_SEC = 60;

    public static final String HEADER_XSRF_AUTH_TOKEN = "X-XSRF-TOKEN";
    public static final String COOKIE_XSRF_AUTH_TOKEN = "XSRF-TOKEN";
    /**
     * 30 seconds in milliseconds
     */
    public static final int TOKEN_EXPIRE_CHECKER_FIXED_RATE = 30000;
    public static final int MAX_TOKEN_EXPIRATION_TIME = 600; // in sec. => 10 min.

    private Constants(){

    }
}
