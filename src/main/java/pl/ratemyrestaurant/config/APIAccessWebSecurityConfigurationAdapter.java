package pl.ratemyrestaurant.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.ratemyrestaurant.filter.AuthFilter;
import pl.ratemyrestaurant.filter.LoginFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class APIAccessWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    private String[] patterns = new String[]{
            "/index.html", "/error", "/register", "/login"
    };
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("pass").roles("USER")
                .and()
                .withUser("admin").password("pass").roles("USER", "ADMIN");
        auth.userDetailsService(userDetailsService);
    }

    protected void configure(HttpSecurity http) throws Exception {

        http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable()
                .authorizeRequests().antMatchers(patterns).permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/api/register").permitAll().and()
                .addFilterBefore(new LoginFilter(new AntPathRequestMatcher("/api/login")), UsernamePasswordAuthenticationFilter.class)
                .antMatcher("/api/**").addFilterAfter(new AuthFilter(), FilterSecurityInterceptor.class);
    }

    @Bean
        public CorsConfigurationSource corsConfigurationSource() {
////        final CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(Arrays.asList("*"));
////        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS"));
////        configuration.setAllowCredentials(true);
////        configuration.setAllowedHeaders(Arrays.asList("x-xsrf-token","*"));
////        configuration.setMaxAge(3600L);
////        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
//
//    @Configuration
//    @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//    public static class AnonymousAccessWebSecurityConfigurationAdapter extends APIAccessWebSecurityConfigurationAdapter {
//        String[] patterns = new String[]{
//                "/index.html", "/error", "/register", "/login"
//        };
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            super.configure(http);
//            http.sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers(patterns)
//                    .permitAll().and()
//                    .addFilterBefore(new LoginFilter(new AntPathRequestMatcher("/api/login")), UsernamePasswordAuthenticationFilter.class);
//            // .and().authorizeRequests().antMatchers("/api/**").authenticated();
//        }
//    }

}
