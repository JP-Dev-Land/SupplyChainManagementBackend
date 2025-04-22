package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderCreateRequest;
import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderDTO;
import com.jpdevland.supply_chain_mgmt.backend.exception.ResourceNotFoundException;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import com.jpdevland.supply_chain_mgmt.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            log.error("Unauthorized attempt to create order: No UserDetails found in security context.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication required to create an order.");
        }

        Long customerId;
        if (userDetails instanceof User) { // Check if the principal is your User entity
            customerId = ((User) userDetails).getId();
        } else {
            log.error("Cannot extract customer ID from principal of type: {}. Username: {}",
                    userDetails.getClass().getName(), userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not determine customer ID from authentication details.");
        }

        log.info("Creating order for customer ID: {}", customerId);

        try {
            OrderDTO createdOrder = orderService.createOrder(request, customerId);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating order for customer {}: {}", customerId, e.getMessage(), e);
            // You might want more specific error handling here based on exception type
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating order: " + e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDTO order = orderService.getOrderById(orderId);
            // Optional: Add checks here if the current user is allowed to view this
            // specific order
            return ResponseEntity.ok(order);
        } catch (ResourceNotFoundException e) {
            log.warn("Order not found: {}", orderId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error fetching order {}: {}", orderId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching order details.");
        }
    }

    // Similar changes for getOrdersByCustomer and getOrdersBySeller if needed
    @GetMapping("/customer")
    public ResponseEntity<?> getOrdersByCustomer(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required.");
        }

        Long customerId;
        if (userDetails instanceof User) {
            customerId = ((User) userDetails).getId();
        } else {
            log.error("Cannot extract customer ID from principal type: {}", userDetails.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not determine customer ID.");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDTO> orders = orderService.getOrdersByCustomer(customerId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/seller")
    public ResponseEntity<?> getOrdersBySeller(@AuthenticationPrincipal UserDetails userDetails, // Changed to
                                                                                                 // UserDetails
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication required.");
        }

        Long sellerId;

        if (userDetails instanceof User) {
            sellerId = ((User) userDetails).getId();
        } else {
            log.error("Cannot extract seller ID from principal type: {}", userDetails.getClass().getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not determine seller ID.");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDTO> orders = orderService.getOrdersBySeller(sellerId, pageable);
        return ResponseEntity.ok(orders);
    }
}