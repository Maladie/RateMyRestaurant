package pl.ratemyrestaurant.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PlacesController {

    @GetMapping(value = "/getPlacesInRadius",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPlacesInRadius(@RequestParam double lng,@RequestParam double lat,@RequestParam double radius, @RequestParam(required = false) String type){

        return lng+" "+lat+" "+radius+" "+type;
    }
}
