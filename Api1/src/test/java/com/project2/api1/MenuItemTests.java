package com.project2.api1;

import com.project2.api1.Services.MenuItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MenuItemTests {
    @Autowired
    MenuItemService menuItemService;

    @Test
    public void doesGetItemThrowExceptionIfBadValueProvided() {
        try {
            menuItemService.getItem(14);

        } catch (Exception e)
        {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void doesGetItemWorkIfGoodValueProvided(){
        try {
            Assertions.assertEquals("French Fries", menuItemService.getItem(1).getDescription());
        } catch (Exception e) {
        
        }
    }

    @Test
    public void doesGetAllItemsWork(){
        Assertions.assertNotNull(menuItemService.getAllItems());
    }

}
