package com.jpdevland.supply_chain_mgmt.backend.dto.order;

import com.jpdevland.supply_chain_mgmt.backend.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

// DTO for updating limited fields, e.g., status or delivery info (before shipping)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdateDto {

    @NotNull(message = "Order status cannot be null")
    private OrderStatus orderStatus; // Primary field to update

    // Optionally allow updating delivery details if status allows
    @Size(max = 255, message = "Delivery city exceeds maximum length")
    private String deliveryCity;
    private Double deliveryLatitude;
    private Double deliveryLongitude;
    @Size(max = 50, message = "Delivery postal code exceeds maximum length")
    private String deliveryPostalCode;
    @Size(max = 255, message = "Delivery street exceeds maximum length")
    private String deliveryStreet;

    // Note: Updating items is typically handled via OrderItem endpoints or specific logic
}
