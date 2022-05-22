package com.project2.api1.Controllers;

import com.project2.api1.Models.Customer;
import com.project2.api1.Models.LoginDTO;
import com.project2.api1.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @GetMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO l) {
      if (customerService.login(l.getPassword(),l.getId())){
          return ResponseEntity.ok(customerService.getCustomer(l.getId()));
        }
      else return ResponseEntity.status(401).body("User credentials incorrect");
    }

    @GetMapping("/logout/{id}")
    public ResponseEntity logout(@PathVariable int id)
    {
        if (customerService.logout(id))
        {
            return ResponseEntity.ok("Logged out successfully");
        }
        else return ResponseEntity.status(401).body("Was unable to log customer out");
    }


}
