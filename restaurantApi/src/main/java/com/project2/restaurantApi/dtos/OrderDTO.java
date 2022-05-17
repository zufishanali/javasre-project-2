package com.project2.restaurantApi.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private int orderId;
    private String contactPreference; // email or phone or both
    private int customerId;
    private String customerEmail;
    private String customerNumber;
    private boolean isPayed;
}
