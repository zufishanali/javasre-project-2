package com.project2.api1.Models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
