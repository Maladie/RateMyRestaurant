package pl.ratemyrestaurant.utils;

public class Util {

    /**
     * Check if string is null or empty
     * @param str string value to check
     * @return true if is null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Object null check
     * @param object object value to check
     * @return true if object is null otherwise false
     */
    public static boolean isNullObject(Object object) {
        return object == null;
    }
}
