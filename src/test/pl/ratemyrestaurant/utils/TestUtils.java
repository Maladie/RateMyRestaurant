package pl.ratemyrestaurant.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T asObjectJsonString(String jsonString, Class<T> jsonObjectType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, jsonObjectType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
