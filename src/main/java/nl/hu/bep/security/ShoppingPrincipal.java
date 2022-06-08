package nl.hu.bep.security;

import java.security.Principal;

public class ShoppingPrincipal implements Principal {
    private String name;

    public ShoppingPrincipal(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
