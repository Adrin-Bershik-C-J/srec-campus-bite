package com.srec.fc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.srec.fc.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByRollNo(String rollNo);

    Optional<User> findByEmail(String email);

    boolean existsByRollNo(String rollNo);

    boolean existsByEmail(String email);
}
