package com.project2.api1.Controllers;

import com.project2.api1.Models.OrderDTO;
import com.project2.api1.Services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Autowired
    OrdersService ordersService;

    @GetMapping("/{id}")
    public ResponseEntity getOrder(@PathVariable int id)
    {
        try {
            return ResponseEntity.ok(ordersService.getItem(id));
        }catch (Exception e)
        {
            return null;
        }
    }

    @PutMapping("place/{id}")
    public ResponseEntity placeOrder(@PathVariable int id)
    {
        ordersService.placeOrder(id);
        return ResponseEntity.ok("");
    }

    @PostMapping("new")
    public ResponseEntity createOrder(@RequestBody OrderDTO o)
    {
        try{
            ResponseEntity re = new ResponseEntity(HttpStatus.FORBIDDEN);

            if (ordersService.isInRange(o.getCustomerId())) {
                return ResponseEntity.ok(ordersService.getItem(ordersService.createOrder(o)));
            }
            else
            {
                return re;
            }
        }catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }

    }

}
