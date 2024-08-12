package org.acme.resource;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import org.acme.entity.Menu;
import org.acme.entity.MenuItem;
import org.acme.entity.Restaurant;

import java.math.BigDecimal;
import java.util.List;

@Path("/restaurant")
@Authenticated
public class RestaurantResource {

    @GET
    @Path("/public/list")
    @PermitAll
    public List<Restaurant> getRestaurants() {
        return Restaurant.listAll();
    }

    @GET
    @Path("/public/menu/{restaurantId}")
    @PermitAll
    public Menu getMenu(@PathParam("restaurantId") Long restaurantId) {
        Menu menu = Menu.find("restaurant = ?1 and active = 1", restaurantId).firstResult();
        menu.setMenuItems(MenuItem.find("menuId = ?1", menu.id).list());
        return menu;
    }

    @POST
    @Transactional
    @RolesAllowed("admin")
    public Restaurant createRestaurant(Restaurant restaurant){
        restaurant.persist();
        return restaurant;
    }

    @POST
    @Path("/menu")
    @Transactional
    @RolesAllowed("manager")
    public Menu createMenu(Menu menu){
        menu.persist();
        menu.getMenuItems().forEach(menuItem -> {
            menuItem.setMenuId(menu.id);
            menuItem.persist();
        });
        return menu;
    }

    @PUT
    @Path("/menu/item/{itemId}/{price}")
    @Transactional
    @RolesAllowed("owner")
    public MenuItem createMenuItem(@PathParam("itemId") Long itemId, @PathParam("price") BigDecimal price){
        MenuItem menuItem = MenuItem.findById(itemId);
        menuItem.setPrice(price);
        menuItem.persist();
        return menuItem;
    }

}
