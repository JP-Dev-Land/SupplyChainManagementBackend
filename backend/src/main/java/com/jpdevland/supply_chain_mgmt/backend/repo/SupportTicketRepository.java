package com.jpdevland.supply_chain_mgmt.backend.repo;

import com.jpdevland.supply_chain_mgmt.backend.model.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    // Add custom query methods if needed, e.g., findByCustomerId

    Page<SupportTicket> findAllByCustomerId(Long customerId, Pageable pageable);
}