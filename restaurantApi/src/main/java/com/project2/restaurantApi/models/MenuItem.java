package com.project2.restaurantApi.models;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="MenuItem")
public class MenuItem {
    @Id
    public int id;
    @NonNull
    public String description;
    @NonNull
    public double cost;
    public String specialInstructions;
}
