package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.Shop;
import nl.hu.bep.shopping.model.Shopper;
import nl.hu.bep.shopping.model.ShoppingList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("shopper")
public class PersonResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Shopper> getShoppers() {
        Shop shop = Shop.getShop();
        return shop.getAllPersons();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShopper(@PathParam("name") String name) {
        Shopper shopper = Shop.getShop().getShopper(name);

        if (shopper == null) {
            return Response.status(404).entity(ErrorResponse.fromString("Shopper niet gevonden")).build();
        } else {
            return Response.ok(shopper).build();
        }
    }

    @GET
    @Path("{name}/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShoppingListsFromPerson(@PathParam("name") String name) {
        Shop shop = Shop.getShop();

        List<ShoppingList> allListsFromPerson = shop.getListFromPerson(name); //warning: might return null!
        if (allListsFromPerson == null) {
            return Response.status(404).entity(ErrorResponse.fromString("Shopper niet gevonden")).build();
        } else {
            return Response.ok(allListsFromPerson).build();
        }
    }
}
