package com.example.airbnb.repository;

import com.example.airbnb.model.OldPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OldPasswordRepository extends JpaRepository<OldPassword, Long> {

}
