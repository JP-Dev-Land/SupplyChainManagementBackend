package com.jpdevland.supply_chain_mgmt.backend.service;

import com.jpdevland.supply_chain_mgmt.backend.dto.CreateSponsoredProductRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.SponsoredProductDTO;

import java.util.List;

public interface SponsoredProductService {
    SponsoredProductDTO createSponsoredProduct(CreateSponsoredProductRequestDTO request);
    List<SponsoredProductDTO> getAllSponsoredProducts();
}
