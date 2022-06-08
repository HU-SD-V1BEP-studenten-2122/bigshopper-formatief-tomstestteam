package nl.hu.bep.shopping.webservices;

import nl.hu.bep.shopping.model.*;
import nl.hu.bep.shopping.webservices.dto.ListItemRequest;
import nl.hu.bep.shopping.webservices.dto.ErrorResponse;
import nl.hu.bep.shopping.webservices.dto.NewListRequest;
import nl.hu.bep.shopping.webservices.dto.UpdateListRequest;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@Path("list")
@RolesAllowed("gebruiker")
public class ListResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ShoppingList> getShoppingLists(@Context SecurityContext currentContext) {
        String currentUser = currentContext.getUserPrincipal().getName();
        return Shop.getShop().getAllShoppingLists(currentUser);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addList(@Context SecurityContext currentContext, NewListRequest newListRequest) {
        Shop shop = Shop.getShop();
        Shopper shopper = shop.getShopper(currentContext.getUserPrincipal().getName());

        if (shopper == null) {
            return Response.status(400).entity(ErrorResponse.fromString("Owner niet gevonden")).build();
        }

        ShoppingList newList = new ShoppingList(newListRequest.name, shopper);
        shopper.addList(newList);

        return Response.ok(newList).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public Response getShoppingListByName(@Context SecurityContext currentContext, @PathParam("name") String name) {
        Shop shop = Shop.getShop();
        ShoppingList list = shop.getShoppingListByName(name);

        if (list == null) {
            return Response.status(404).entity(ErrorResponse.fromString("List not found")).build();
        } else {
            boolean isFromDifferentOwner = !(list.getOwner().getName().equals(currentContext.getUserPrincipal().getName()));

            if (isFromDifferentOwner) {
                return Response.status(403).entity(ErrorResponse.fromString("List not yours")).build();
            } else {
                return Response.ok(list).build();
            }

        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public Response updateList(@Context SecurityContext currentContext, @PathParam("name") String name, UpdateListRequest updateListRequest) {
        Shop shop = Shop.getShop();
        ShoppingList list = shop.getShoppingListByName(name);
        if (list == null) {
            return Response.status(404).entity(ErrorResponse.fromString("List not found")).build();
        }
        boolean isFromDifferentOwner = !(list.getOwner().getName().equals(currentContext.getUserPrincipal().getName()));
        if (isFromDifferentOwner) {
            return Response.status(403).entity(ErrorResponse.fromString("List not yours")).build();
        }
        Shopper nieuweOwner = Shop.getShop().getShopper(updateListRequest.owner);
        if (nieuweOwner == null) {
            return Response.status(400).entity(ErrorResponse.fromString("Owner not found")).build();
        }
        for (ListItemRequest itemRequest : updateListRequest.listItems) {
            Product product = shop.getProduct(itemRequest.name);
            if (product == null) {
                return Response.status(400).entity(ErrorResponse.fromString(String.format("Product %s not found", itemRequest.name))).build();
            }
        }

        list.clear();
        for (ListItemRequest itemRequest : updateListRequest.listItems) {
            Product product = shop.getProduct(itemRequest.name);
            list.addItem(product, itemRequest.amount);
        }

        nieuweOwner.addList(list);

        return Response.ok(list).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}")
    public Response addItemToList(@Context SecurityContext currentContext, @PathParam("name") String name, ListItemRequest addItemRequest) {
        Shop shop = Shop.getShop();
        ShoppingList list = shop.getShoppingListByName(name);
        Product product = shop.getProduct(addItemRequest.name);

        if (list == null) {
            return Response.status(404).entity(ErrorResponse.fromString("List not found")).build();
        }
        if (product == null) {
            return Response.status(400).entity(ErrorResponse.fromString("Product not found")).build();
        }
        boolean isFromDifferentOwner = !(list.getOwner().getName().equals(currentContext.getUserPrincipal().getName()));
        if(isFromDifferentOwner) {
            return Response.status(403).entity(ErrorResponse.fromString("List not yours")).build();
        }

        list.addItem(product, addItemRequest.amount);
        return Response.ok(list).build();

    }
}
