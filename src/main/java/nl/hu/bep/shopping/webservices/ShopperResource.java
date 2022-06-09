package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.Shop;
import nl.hu.bep.shopping.model.Shopper;
import nl.hu.bep.shopping.model.ShoppingList;
import nl.hu.bep.shopping.webservices.dto.ErrorResponse;
import nl.hu.bep.shopping.webservices.dto.NewShopperRequest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("shopper")
@RolesAllowed("admin")
public class ShopperResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Shopper> getShoppers() {
        Shop shop = Shop.getShop();
        return shop.getAllPersons();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addShopper(NewShopperRequest newShopperReq){
        Shopper newShopper = new Shopper(newShopperReq.name);
        return Response.ok(newShopper).build();
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

    @DELETE
    @Path("{name}")
    public Response removeShopper(@PathParam("name") String name) {
        Shopper shopper = Shop.getShop().getShopper(name);

        if (shopper == null) {
            return Response.status(404).entity(ErrorResponse.fromString("Shopper niet gevonden")).build();
        } else {
            Shop.getShop().removeShopper(shopper);
            return Response.noContent().build();
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
