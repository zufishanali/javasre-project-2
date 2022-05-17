package com.project2.restaurantApi.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Customer")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Customer {
    @Id
    private int id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String emailAddress;
    @NonNull
    private String password;
    private String address;
    private String city;
    private String state;
    private String areaCode;
    private boolean loggedIn;
    private String contactType;
}
