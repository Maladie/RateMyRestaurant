package pl.ratemyrestaurant.service;

import pl.ratemyrestaurant.exception.TokenException;
import pl.ratemyrestaurant.model.User;

public interface TokenHandlerService {

     User parseUserFromToken(String token) throws TokenException;

     String calculateTokenForUser(User user);

     void insertToCache(String token, User user);


}
