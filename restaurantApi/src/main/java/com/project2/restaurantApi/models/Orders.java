package com.project2.restaurantApi.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Orders")
public class Orders {
    @Id
    private int orderId;
    @NonNull
    private int customerId;
    @NonNull
    private LocalDateTime orderOn;
    private LocalDateTime fulfilledOn;
    private String status;

    @OneToMany
    List<MenuItem> items;
}
