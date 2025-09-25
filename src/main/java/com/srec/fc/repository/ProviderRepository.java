package com.srec.fc.repository;

import com.srec.fc.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    // find provider by email (useful for login/validation)
    Optional<Provider> findByEmail(String email);

    // check if a provider exists by name
    boolean existsByName(String name);

    // check if a provider exists by email
    boolean existsByEmail(String email);
}
