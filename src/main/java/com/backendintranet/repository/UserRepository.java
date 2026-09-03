package com.backendintranet.repository;

import com.backendintranet.entity.User;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<User>findByUsername(String username);

    boolean existsByUsername(String Username);

    boolean existsByCedula(String cedula);
}
