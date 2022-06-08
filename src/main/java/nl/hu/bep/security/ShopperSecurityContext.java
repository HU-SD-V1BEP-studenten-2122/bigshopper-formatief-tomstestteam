package nl.hu.bep.security;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class ShopperSecurityContext implements SecurityContext {
    private Principal currentPrincipal;

    public ShopperSecurityContext(Principal currentPrincipal) {
        this.currentPrincipal = currentPrincipal;
    }

    @Override
    public Principal getUserPrincipal() {
        return currentPrincipal;
    }

    @Override
    public boolean isUserInRole(String role) {
        if (this.currentPrincipal.getName().equals("admin") && role.equals("admin")) {
            return true;
        } else if (this.currentPrincipal.getName().equals("tom") && role.equals("gebruiker")) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "JWT";
    }
}
