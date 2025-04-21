package com.jpdevland.supply_chain_mgmt.backend.controller;

import com.jpdevland.supply_chain_mgmt.backend.dto.CreateSponsoredProductRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.SponsoredProductDTO;
import com.jpdevland.supply_chain_mgmt.backend.service.SponsoredProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sponsored-products")
@RequiredArgsConstructor
public class SponsoredProductController {

    private final SponsoredProductService sponsoredProductService;

    @PostMapping
    public ResponseEntity<SponsoredProductDTO> createSponsoredProduct(@RequestBody CreateSponsoredProductRequestDTO request) {
        SponsoredProductDTO created = sponsoredProductService.createSponsoredProduct(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<SponsoredProductDTO>> getAllSponsoredProducts() {
        return ResponseEntity.ok(sponsoredProductService.getAllSponsoredProducts());
    }
}
