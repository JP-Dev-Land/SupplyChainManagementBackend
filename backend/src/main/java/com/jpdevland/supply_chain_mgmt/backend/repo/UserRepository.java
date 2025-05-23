package com.jpdevland.supply_chain_mgmt.backend.repo;

import com.jpdevland.supply_chain_mgmt.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByAvailableTrue();
}