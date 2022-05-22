package com.project2.api1.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferOrderDTO {
    private int orderId;
    private String contactPreference; // email or sms or both
    private String customerEmail;
    private String customerNumber;
}
