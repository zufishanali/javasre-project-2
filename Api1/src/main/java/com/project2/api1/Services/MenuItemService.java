package com.project2.api1.Services;

import com.project2.api1.Models.MenuItem;
import com.project2.api1.Repositories.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    @Autowired
    MenuItemRepository menuItemRepository;


    public MenuItem getItem(int id) throws Exception {
        if (menuItemRepository.findById(id).isPresent()) {
            return menuItemRepository.findById(id).get();
        }
        else
            throw new Exception();
    }

    public List<MenuItem> getAllItems() {

        return menuItemRepository.findAll();
    }

}
