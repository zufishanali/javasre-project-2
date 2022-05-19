package com.project2.api1.Controllers;

import com.project2.api1.Models.MenuItem;
import com.project2.api1.Services.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    MenuItemService menuItemService;

    @GetMapping("getAll")
    public ResponseEntity getMenuItems(){
        return ok(menuItemService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity getItem(@PathVariable int id) {
        try {
            return ResponseEntity.ok(menuItemService.getItem(id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }
}
