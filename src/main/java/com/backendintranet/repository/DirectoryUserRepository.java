package com.backendintranet.repository;

import com.backendintranet.entity.DirectoryUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DirectoryUserRepository extends JpaRepository <DirectoryUser, String> {}
