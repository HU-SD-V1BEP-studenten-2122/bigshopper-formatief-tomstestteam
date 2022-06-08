package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.Product;
import nl.hu.bep.shopping.model.Shop;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("product")
public class ProductResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProducts() {
        return Shop.getShop().getAllProducts();

    }
}
