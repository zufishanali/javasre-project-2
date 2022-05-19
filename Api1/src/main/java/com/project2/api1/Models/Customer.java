package com.project2.api1.Models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
