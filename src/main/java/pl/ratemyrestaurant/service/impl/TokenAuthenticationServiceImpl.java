package pl.ratemyrestaurant.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ratemyrestaurant.config.Constants;
import pl.ratemyrestaurant.domain.UserToken;
import pl.ratemyrestaurant.exception.TokenException;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.model.User;
import pl.ratemyrestaurant.model.UserAuthentication;
import pl.ratemyrestaurant.repository.UserRepository;
import pl.ratemyrestaurant.repository.UserTokenRepository;
import pl.ratemyrestaurant.service.TokenAuthenticationService;
import pl.ratemyrestaurant.service.TokenHandlerService;
import pl.ratemyrestaurant.type.APIInfoCodes;
import pl.ratemyrestaurant.type.TokenStatus;
import pl.ratemyrestaurant.utils.CheckingUtils;
import pl.ratemyrestaurant.utils.SecurityUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {
    private static final String AUTH_HEADER_NAME = "X-XSRF-TOKEN";
    private static Logger logger = LogManager.getLogger(TokenAuthenticationServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private TokenHandlerService tokenHandlerService;

    @Override
    public Authentication getAuthenticationForLogin(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user = null;

        String username = "";
        String password = "";
        if (request.getHeader("Content-Type") != null && request.getContentType().equals("application/json")) {

            ObjectMapper mapper = new ObjectMapper();
            try {
                String body =request.getReader().lines().collect(Collectors.joining());
                if(!CheckingUtils.isNullOrEmpty(body)) {
                    TypeReference<HashMap<String,String>> typeRef
                            = new TypeReference<HashMap<String,String>>() {};
                    Map<String, String> s = mapper.readValue(body, typeRef);
                    username = s.get("username");
                    password = s.get("password");
                }
            } catch (IOException e) {
                logger.debug("IOException - authentication for login ", e);
            }
        } else {
            username = request.getParameter("username");
            password = request.getParameter("password");
        }


        UserAuthentication auth = new UserAuthentication(null);

        Info info = auth.getInfo();

        boolean isUsernameEmpty = CheckingUtils.isNullOrEmpty(username);
        boolean isAuthorized = false;

        if (isUsernameEmpty) {
            info.setHttpStatusCode(400L);
            info.setInfoCode(APIInfoCodes.INVALID_USERNAME);
            info.setDesc("User empty username");
        } else {
            if (CheckingUtils.isNullOrEmpty(password)) {
                info.setHttpStatusCode(400L);
                info.setInfoCode(APIInfoCodes.INVALID_PASSWORD);
                info.setDesc("User empty password");
            } else {
                user = userRepository.findByUsernameIgnoreCase(username);

                if (CheckingUtils.isNullObject(user)) {
                    info.setHttpStatusCode(400L);
                    info.setDesc("User not found");
                    info.setInfoCode(APIInfoCodes.USERNAME_NOT_FOUND);
                } else if (CheckingUtils.isNullOrEmpty(user.getPassword())) {
                    info.setHttpStatusCode(400L);
                    info.setInfoCode(APIInfoCodes.INVALID_PASSWORD);
                    info.setDesc("User missing password");
                } else {
                    if (!SecurityUtils.isPasswordMatch(password, user.getSalt(), user.getPassword())) {
                        // Password
                        // not
                        // equal
                        info.setHttpStatusCode(400L);
                        info.setDesc("User wrong password");
                        info.setInfoCode(APIInfoCodes.WRONG_USER_PASSWORD);
                    } else { // user is authorized successfully
                        isAuthorized = true;
                    }
                }
            }
        }

        if (isAuthorized) {
            info.setHttpStatusCode(200L);
            info.setInfoCode(APIInfoCodes.OK);
            info.setDesc("Basic");
            auth.setUser(user);
            auth.setInfo(info);
            auth.setAuthenticated(true);
            return auth;
        }
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        auth.setAuthenticated(false);
        return auth;
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String token = request.getHeader(AUTH_HEADER_NAME);

        UserAuthentication auth = new UserAuthentication(null);

        Info info = auth.getInfo();

        if (!CheckingUtils.isNullOrEmpty(token)) {
            try {
                User user = tokenHandlerService.parseUserFromToken(token);

                if (!CheckingUtils.isNullObject(user)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    auth.setUser(user);
                    auth.setAuthenticated(true);
                    auth.setToken(token);
                    logger.debug("User "+user.getUsername()+ " authentication success! Token: "+ token);
                    return auth;
                } else {
                    info.setHttpStatusCode(101L);
                    info.setDesc("User not found. Invalid token.");
                    logger.debug("User not found. Invalid token. Authentication failed! Token: "+ token);
                }
            } catch (TokenException e) {
                info.setHttpStatusCode(102L);
                info.setDesc(e.getDescription());
                logger.debug("TokenException "+info.getHttpStatusCode() + " "+ e.getDescription());
            } catch (Exception e) {
                info.setHttpStatusCode(103L);
                info.setDesc("AUTHENTICATE_EXCEPTION_RELOGIN_NEEDED");
                logger.catching(e);
            }

        } else {
            info.setHttpStatusCode(100L);
            info.setDesc("Authorization Token not found");
            logger.debug("Authorization Token not found. Token value: "+ token);
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        auth.setAuthenticated(false);
        return auth;
    }

    @Override
    public UserToken addAuthentication(HttpServletResponse response, UserAuthentication authResult) {
        final User user = authResult.getDetails();
        String token = tokenHandlerService.calculateTokenForUser(user);

        UserToken userToken = new UserToken();
        userToken.setUser(user);
        userToken.setToken(token);
        userToken.setStatus(TokenStatus.ACTIVE.getTokenStatus());
        userToken.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        //hopefully removes all tokens cached by user
       int result = userTokenRepository.deactivateAllTokensByUser(user.getId());
       logger.debug("Deactivation status: "+ result);
        //apply all changes to db instantly
        userTokenRepository.flush();
        userTokenRepository.save(userToken);
        tokenHandlerService.insertToCache(token, user);

        Cookie cookie = new Cookie(Constants.COOKIE_XSRF_AUTH_TOKEN, token);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        response.addHeader(Constants.HEADER_XSRF_AUTH_TOKEN, token);
        return userToken;
    }
}
