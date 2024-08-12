package org.acme.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.entity.Order;
import org.acme.entity.OrderItem;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.List;

@Path("/order")
//@Authenticated
public class OrderResource {

    @GET
    @Path("/{restaurantId}/list")
    @SecurityRequirement(name = "Keycloak")
//    @RolesAllowed("manager")
    public List<Order> getOrder(@PathParam("restaurantId") Long restaurantId) {
        return Order.find("restaurantId = ?1", restaurantId).list();
    }

    @GET
    @Path("/{orderId}")
    @SecurityRequirement(name = "Keycloak")
//    @RolesAllowed("manager")
    public Order getOrderDetails(@PathParam("orderId") Long orderId){
        Order order = Order.findById(orderId);
        order.setOrderItems(OrderItem.find("orderId = ?1", orderId).list());
        return order;
    }

    @POST
    @Transactional
    @SecurityRequirement(name = "Keycloak")
    public Order createOrder(Order order) {
        order.persist();
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> {
            orderItem.setOrderId(order.id);
            orderItem.persist();
        });
        return order;
    }
}
