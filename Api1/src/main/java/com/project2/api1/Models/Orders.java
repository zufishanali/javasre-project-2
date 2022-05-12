package com.project2.api1.Models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
