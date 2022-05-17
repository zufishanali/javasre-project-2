package com.project2.restaurantApi.dtos;

import com.project2.restaurantApi.models.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class OrderStatusUpdateDTO {
    private int orderId;
    private Status orderStatus;
}
