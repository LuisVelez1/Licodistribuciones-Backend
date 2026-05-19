package com.backendintranet.repository;

import com.backendintranet.entity.BirthdayUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BirthdayUserRepository extends JpaRepository<BirthdayUser, String> {}
