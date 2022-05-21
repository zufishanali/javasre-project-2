package com.project2.restaurantApi.dtos;

import com.project2.restaurantApi.models.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {
    private int orderId;
    private Status orderStatus;
    private String contactPreference;
    private String customerEmail;
    private String customerNumber;
}
