package pl.ratemyrestaurant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PlacesController {

//    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/getPlacesInRadius")
    public String getPlacesInRadius(@RequestParam double lng, @RequestParam double lat, @RequestParam double radius, @RequestParam String type) {
        ObjectMapper mapper= new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("lng", lng);
        map.put("lat", lat);
        map.put("radius", radius);
        map.put("type", type);
        String string = "";
        try {
            string = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }
}
