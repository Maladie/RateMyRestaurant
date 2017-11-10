package pl.ratemyrestaurant.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import pl.ratemyrestaurant.config.ApplicationContextProvider;
import pl.ratemyrestaurant.domain.UserToken;
import pl.ratemyrestaurant.exception.UserAuthenticationException;
import pl.ratemyrestaurant.model.Info;
import pl.ratemyrestaurant.model.UserAuthentication;
import pl.ratemyrestaurant.service.TokenAuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    public LoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
        tokenAuthenticationService= (TokenAuthenticationService) ApplicationContextProvider.getApplicationContext()
                .getBean("tokenAuthenticationServiceImpl");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        UserAuthentication auth = (UserAuthentication) tokenAuthenticationService.getAuthenticationForLogin(request,response);
        if(!auth.isAuthenticated()){
            throw new UserAuthenticationException("Authentication failed", auth);
        }
        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserToken token = null;
        try {
            UserAuthentication authResultObject = (UserAuthentication) authResult;
            token = tokenAuthenticationService.addAuthentication(response, authResultObject);
            System.out.println("Authentication SUCCESS. "+"UserId: " + authResultObject.getUser().getId()+ " token: "+ token.getToken());

            // Add the authentication to the Security context
            SecurityContextHolder.getContext().setAuthentication(authResult);

            HashMap<String, Object> information = new HashMap<>();
            information.put("USER", authResultObject.getUser());
            information.put("INFO", authResultObject.getInfo());

            ObjectMapper mapper = new ObjectMapper();
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(mapper.writeValueAsString(information));
        } catch (Exception ex) {
            Info i = new Info();
            i.setCode(103L);
            i.setDesc("AUTHENTICATION_EXCEPTION_RELOGIN_NEEDED");

            System.out.println("Authentication EXCEPTION. " + "Invalid token: " + token +"\n");
            ex.printStackTrace();
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(i));
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        UserAuthenticationException authException = (UserAuthenticationException) failed;
        Info info = authException.getAuthentication().getInfo();

        response.setHeader("X-AUTH-ERR-DESC", info.getCode() + "-" + info.getDesc());

        ObjectMapper mapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(authException.getAuthentication().getInfo()));
        System.out.println("Autentication FAIL. " + "Username" + request.getHeader("username") + ". Additional info: "+info);
    }
}
