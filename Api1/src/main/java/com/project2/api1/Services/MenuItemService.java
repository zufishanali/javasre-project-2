package com.project2.api1.Services;

import com.project2.api1.Repositories.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {

    //Wiring in MenuItem Repository
    @Autowired
    MenuItemRepository menuItemRepository;

    /*
        Create MenuItem service methods here
    */
}
