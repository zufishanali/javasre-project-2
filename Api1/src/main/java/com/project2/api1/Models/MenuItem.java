package com.project2.api1.Models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class MenuItem {
    @Id
    public int id;
    @NonNull
    public String description;
    @NonNull
    public double cost;
    public String specialInstructions;

}
