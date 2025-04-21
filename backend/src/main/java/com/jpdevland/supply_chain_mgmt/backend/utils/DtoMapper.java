package com.jpdevland.supply_chain_mgmt.backend.utils;

import com.jpdevland.supply_chain_mgmt.backend.dto.ai.AiImageDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.order.OrderItemDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.product.ProductVariantDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.user.UserDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.*; // Import all entities
import org.springframework.stereotype.Component;


import java.util.Collections;
import java.util.stream.Collectors;

@Component // Make it a Spring bean if needed, or keep as static utility
public class DtoMapper {

    public UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
//        dto.setEmail(user.getEmail());
//        dto.setPhone(user.getPhone());
//        dto.setEnabled(user.isEnabled());
//        dto.setCreatedAt(user.getCreatedAt());
//        if (user.getUserRoles() != null) {
//            dto.setRoles(user.getUserRoles().stream()
//                    .map(userRole -> userRole.getRole().name())
//                    .collect(Collectors.toSet()));
//        } else {
//            dto.setRoles(Collections.emptySet());
//        }
        return dto;
    }

    public ProductVariantDTO toProductVariantDTO(ProductVariant variant) {
        if (variant == null) return null;
        ProductVariantDTO dto = new ProductVariantDTO();
        dto.setId(variant.getId());
        dto.setVariantName(variant.getVariantName());
        dto.setPriceModifier(variant.getPriceModifier());
        dto.setStockQuantity(variant.getStockQuantity());
        return dto;
    }


    public ProductDTO toProductDTO(Product product) {
        if (product == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        if (product.getSeller() != null) {
            dto.setSellerId(product.getSeller().getId());
            dto.setSellerName(product.getSeller().getName()); // Include seller name
        }
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setBasePrice(product.getBasePrice());
        dto.setAvailable(product.isAvailable());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        if (product.getVariants() != null) {
            dto.setVariants(product.getVariants().stream()
                    .map(this::toProductVariantDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setVariants(Collections.emptyList());
        }
        // TODO: Map other lists like discounts, policies, aiImages as needed

        return dto;
    }

//    public OrderItemDTO toOrderItemDTO(OrderItem item) {
//        if (item == null) return null;
//        OrderItemDTO dto = new OrderItemDTO();
//        dto.setId(item.getId());
//        if (item.getProductVariant() != null) {
//            dto.setProductVariantId(item.getProductVariant().getId());
//            dto.setVariantName(item.getProductVariant().getVariantName());
//            // Include base product name too
//            if (item.getProductVariant().getProduct() != null) {
//                dto.setProductName(item.getProductVariant().getProduct().getName());
//            }
//        }
//        dto.setQuantity(item.getQuantity());
//        dto.setPriceAtOrder(item.getPriceAtOrder());
//        return dto;
//    }


//    public OrderDTO toOrderDTO(Order order) {
//        if (order == null) return null;
//        OrderDTO dto = new OrderDTO();
//        dto.setId(order.getId());
//        if (order.getCustomer() != null) {
//            dto.setCustomerId(order.getCustomer().getId());
//            dto.setCustomerName(order.getCustomer().getName());
//        }
//        if (order.getSeller() != null) {
//            dto.setSellerId(order.getSeller().getId());
//            dto.setSellerName(order.getSeller().getName());
//        }
//        dto.setOrderStatus(order.getOrderStatus());
//        dto.setTotalAmount(order.getTotalAmount());
//        dto.setDeliveryCity(order.getDeliveryCity());
//        dto.setDeliveryLatitude(order.getDeliveryLatitude());
//        dto.setDeliveryLongitude(order.getDeliveryLongitude());
//        dto.setDeliveryPostalCode(order.getDeliveryPostalCode());
//        dto.setDeliveryStreet(order.getDeliveryStreet());
//        dto.setCreatedAt(order.getCreatedAt());
//        dto.setUpdatedAt(order.getUpdatedAt());
//
//        if (order.getOrderItems() != null) {
//            dto.setOrderItems(order.getOrderItems().stream()
//                    .map(this::toOrderItemDTO)
//                    .collect(Collectors.toList()));
//        } else {
//            dto.setOrderItems(Collections.emptyList());
//        }
//        // TODO: Map payments, tracking info if needed in the main Order DTO
//        return dto;
//    }


//    public AiImageDTO toAiImageDTO(ProductAiImage image) {
//        if (image == null) return null;
//        AiImageDTO dto = new AiImageDTO();
//        dto.setId(image.getId());
//        if (image.getProduct() != null) {
//            dto.setProductId(image.getProduct().getId());
//        }
//        dto.setImageUrl(image.getImageUrl());
//        dto.setGeneratedAt(image.getGeneratedAt());
//        return dto;
//    }

    // Add mappers for other DTOs/Entities as required...
}
