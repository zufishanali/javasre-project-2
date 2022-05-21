package com.project2.restaurantApi.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @NonNull
    private int customerId;
    @NonNull
    private LocalDateTime orderOn;
    private LocalDateTime fulfilledOn;
    private String status;

    @ManyToMany
    List<MenuItem> items;
}
