package com.jpdevland.supply_chain_mgmt.backend.service.impl;

import com.jpdevland.supply_chain_mgmt.backend.dto.CreateSponsoredProductRequestDTO;
import com.jpdevland.supply_chain_mgmt.backend.dto.SponsoredProductDTO;
import com.jpdevland.supply_chain_mgmt.backend.model.Product;
import com.jpdevland.supply_chain_mgmt.backend.model.SponsoredProduct;
import com.jpdevland.supply_chain_mgmt.backend.model.User;
import com.jpdevland.supply_chain_mgmt.backend.repo.ProductRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.SponsoredProductRepository;
import com.jpdevland.supply_chain_mgmt.backend.repo.UserRepository;
import com.jpdevland.supply_chain_mgmt.backend.service.SponsoredProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SponsoredProductServiceImpl implements SponsoredProductService {

    private final SponsoredProductRepository sponsoredProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public SponsoredProductDTO createSponsoredProduct(CreateSponsoredProductRequestDTO request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User sponsor = userRepository.findById(request.getSponsorId())
                .orElseThrow(() -> new RuntimeException("Sponsor (User) not found"));

        SponsoredProduct sponsoredProduct = SponsoredProduct.builder()
                .product(product)
                .sponsor(sponsor)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .adText(request.getAdText())
                .budget(request.getBudget())
                .build();

        sponsoredProduct = sponsoredProductRepository.save(sponsoredProduct);

        return toDTO(sponsoredProduct);
    }

    @Override
    public List<SponsoredProductDTO> getAllSponsoredProducts() {
        return sponsoredProductRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private SponsoredProductDTO toDTO(SponsoredProduct sp) {
        SponsoredProductDTO dto = new SponsoredProductDTO();
        dto.setId(sp.getId());
        dto.setProductId(sp.getProduct().getId());
        dto.setSponsorId(sp.getSponsor().getId());
        dto.setStartDate(sp.getStartDate());
        dto.setEndDate(sp.getEndDate());
        dto.setAdText(sp.getAdText());
        dto.setBudget(sp.getBudget());
        return dto;
    }
}
