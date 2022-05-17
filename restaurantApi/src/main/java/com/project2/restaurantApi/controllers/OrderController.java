package com.project2.restaurantApi.controllers;

import com.project2.restaurantApi.dtos.OrderDTO;
import com.project2.restaurantApi.dtos.OrderStatusUpdateDTO;
import com.project2.restaurantApi.models.Orders;
import com.project2.restaurantApi.repositories.OrdersRepository;
import com.project2.restaurantApi.services.OrderService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
public class OrderController {

    @Setter(onMethod = @__({@Autowired}) )
    private OrderService orderService;


    @GetMapping("/orders")
    public ResponseEntity getAllActiveOrders(){
        return ResponseEntity.ok(orderService.getAllActiveOrders());
    }

    @GetMapping("/order/{id}")
    public ResponseEntity getOrderById(@PathVariable int id){

       Optional<Orders> order = orderService.getOrderById(id);
       if(order.isPresent()) {
           return ResponseEntity.ok(order);
       }
       return ResponseEntity.notFound().build();
    }

    @PutMapping("/order/update")
    public ResponseEntity updateOrderStatus(@RequestBody OrderStatusUpdateDTO o){
        try {
            orderService.updateOrderStatus(o.getOrderId(), o.getOrderStatus());
            return ResponseEntity.ok("Updated Successfully");
        } catch(RuntimeException e){
            return ResponseEntity.internalServerError().body("Error updating order " + e.getMessage());
        }
    }

    @GetMapping("/getOrder")
    public ResponseEntity receiveOrder(@RequestBody OrderDTO orderDto){
        System.out.println("Order received ... processing");
        String response = orderService.confirmOrder(orderDto);
        return ResponseEntity.ok(response);
    }

}
