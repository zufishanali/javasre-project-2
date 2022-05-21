package com.project2.restaurantApi.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="Customer")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String emailAddress;
    @NonNull
    private String password;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String areaCode;
    private boolean loggedIn;
    private LocalDateTime sessionEnds;
    private String contactType;
}
