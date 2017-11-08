package pl.ratemyrestaurant.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import pl.ratemyrestaurant.config.ApplicationContextProvider;
import pl.ratemyrestaurant.model.UserAuthentication;
import pl.ratemyrestaurant.service.TokenAuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {


    @Autowired
    @Qualifier("tokenAuthenticationServiceImpl")
    private TokenAuthenticationService tokenAuthenticationService;

    public AuthFilter() {
        tokenAuthenticationService = (TokenAuthenticationService) ApplicationContextProvider.getApplicationContext()
                .getBean("tokenAuthenticationServiceImpl");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//        if(httpRequest.getContextPath().equals("/register"))
        UserAuthentication auth = (UserAuthentication) tokenAuthenticationService.getAuthentication(httpRequest, httpResponse);
        SecurityContextHolder.getContext().setAuthentication(auth);

        if(auth.isAuthenticated())
            filterChain.doFilter(servletRequest, servletResponse);
        else{
            ObjectMapper mapper = new ObjectMapper();
            servletResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().write(mapper.writeValueAsString(auth.getInfo()));
        }

    }
}
