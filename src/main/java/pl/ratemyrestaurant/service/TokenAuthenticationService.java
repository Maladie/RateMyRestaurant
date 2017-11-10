package pl.ratemyrestaurant.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import pl.ratemyrestaurant.domain.UserToken;
import pl.ratemyrestaurant.model.UserAuthentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface TokenAuthenticationService {

    public Authentication getAuthenticationForLogin(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException;
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public UserToken addAuthentication(HttpServletResponse response, UserAuthentication authResult);
}