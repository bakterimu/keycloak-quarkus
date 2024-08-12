package org.acme.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.entity.Menu;
import org.acme.entity.MenuItem;
import org.acme.entity.Restaurant;

import java.util.List;

@Path("/restaurant")
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
        Menu menu = Menu.find("restaurantId = ?1 and active = 1", restaurantId).firstResult();
        menu.setMenuItems(MenuItem.find("menuId = ?1", menu.id).list());
        return menu;
    }

    @POST
    @Transactional
    @RolesAllowed("admin")

}
