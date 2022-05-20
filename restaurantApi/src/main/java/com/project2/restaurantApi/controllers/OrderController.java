package com.project2.restaurantApi.controllers;

import com.project2.restaurantApi.controllers.exceptions.InvalidStatusArgumentException;
import com.project2.restaurantApi.dtos.OrderDTO;
import com.project2.restaurantApi.controllers.exceptions.OrderNotFoundException;
import com.project2.restaurantApi.models.Orders;
import com.project2.restaurantApi.models.Status;
import com.project2.restaurantApi.services.OrderService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
public class OrderController {

    final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Setter(onMethod = @__({@Autowired}) )
    private OrderService orderService;


    /**
     * Endpoint to view all orders that are placed and not yet completed
     * @return List of orders
     */
    @GetMapping("/orders")
    public ResponseEntity getAllActiveOrders(){
        logger.info("Getting all orders");
        return ResponseEntity.ok(orderService.getAllActiveOrders());
    }


    /**
     * Endpoint to get a specific order with the id specified
     * @param id
     * @return Order object
     */
    @GetMapping("/order/{id}")
    public ResponseEntity getOrderById(@PathVariable int id){
        logger.info("Getting order by id: "+id);
        try{
             Optional<Orders> order = orderService.getOrderById(id);
             return ResponseEntity.ok(order);
        }catch(OrderNotFoundException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Endpoint to get all orders that were set to completed within the last 15 minutes
     * @return List of orders
     */
    @GetMapping("/orders/completed")
    public ResponseEntity getCompletedOrders(){
        logger.info("Getting completed orders");
        return ResponseEntity.ok(orderService.getRecentCompletedOrders(15));
    }


    /**
     * Endpoint to update the order status to one of the options specified in enum Status
     * @param orderDto
     * @return
     */
    @PutMapping("/order/update")
    public ResponseEntity updateOrderStatus(@RequestBody OrderDTO orderDto){
        logger.info("Updating order status");
        try{
            if(orderDto.getOrderStatus().equals(Status.CANCELLED)) {
                orderService.cancelOrder(orderDto);
            } else {
                orderService.updateOrderStatus(orderDto);
            }
            logger.info("Order status successfully updated");
            return ResponseEntity.ok("Updated Successfully");
        } catch(OrderNotFoundException | InvalidStatusArgumentException e){
            logger.info("Error updating order");
            return ResponseEntity.internalServerError().body("Error updating order " + e.getMessage());
        }
    }


    /**
     * Endpoint used by API 1 (customerAPI) to ping this API when an order is placed.
     * This endpoint triggers the order being received and confirmed
     * @param orderDto
     * @return
     */
    @PutMapping("/getOrder")
    public ResponseEntity receiveOrder(@RequestBody OrderDTO orderDto){
        logger.info("Order received ... processing");
        try {
            String response = orderService.confirmOrder(orderDto);
            return ResponseEntity.ok(response);
        }catch(OrderNotFoundException e){
            logger.warn("Issue occurred during order confirmation");
            return ResponseEntity.internalServerError().body("Issue confirming order: " + e.getMessage());
        }
    }
}
