package pl.ratemyrestaurant.utils;

public class Util {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNullObject(Object object) {
        return object == null;
    }
}
