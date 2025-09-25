package com.srec.fc.repository;

import com.srec.fc.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    // find provider by user email
    @Query("SELECT p FROM Provider p WHERE p.user.email = :email")
    Optional<Provider> findByEmail(String email);

    // check if a provider exists by providerName
    boolean existsByProviderName(String providerName);

    // check if a provider exists by user.email
    @Query("SELECT COUNT(p) > 0 FROM Provider p WHERE p.user.email = :email")
    boolean existsByUserEmail(String email);

}
