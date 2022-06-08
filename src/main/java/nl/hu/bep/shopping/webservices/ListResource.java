package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("list")
public class ListResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ShoppingList> getShoppingLists() {
        return Shop.getShop().getAllShoppingLists();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public Response getShoppingListByName(@PathParam("name") String name) {
        Shop shop = Shop.getShop();
        ShoppingList list = shop.getShoppingListByName(name);

        if (list == null) {
            return Response.status(404).entity(ErrorResponse.fromString("List not found")).build();
        } else {
            return Response.ok(list).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addList(NewListRequest newListRequest) {
        Shop shop = Shop.getShop();
        Shopper shopper = shop.getShopper(newListRequest.owner);

        if (shopper == null) {
            return Response.status(400).entity(ErrorResponse.fromString("Owner niet gevonden")).build();
        }

        ShoppingList newList = new ShoppingList(newListRequest.name, shopper);
        shopper.addList(newList);

        return Response.ok(newList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public Response addItemToList(@PathParam("name") String name, AddItemRequest addItemRequest) {
        Shop shop = Shop.getShop();
        ShoppingList list = shop.getShoppingListByName(name);
        Product product = shop.getProduct(addItemRequest.name);

        if (list == null) {
            return Response.status(404).entity(ErrorResponse.fromString("List not found")).build();
        } else if (product == null) {
            return Response.status(400).entity(ErrorResponse.fromString("Product not found")).build();
        } else {
            list.addItem(product, addItemRequest.amount);
            return Response.ok(list).build();
        }
    }
}
