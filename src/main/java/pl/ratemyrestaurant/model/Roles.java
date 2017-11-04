package pl.ratemyrestaurant.model;

import org.springframework.security.core.GrantedAuthority;

//TODO dokończyć i do bazy...
public class Roles implements GrantedAuthority {

    private static String authority = "ADMIN";
    @Override
    public String getAuthority() {
        return authority;
    }
}
