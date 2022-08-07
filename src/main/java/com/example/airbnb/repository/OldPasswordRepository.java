package com.example.airbnb.repository;

import com.example.airbnb.model.OldPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;


@Repository
public interface OldPasswordRepository extends JpaRepository<OldPassword, Long> {
    @Query(value = "select * from old_password where user_id = :id order by id DESC limit 3", nativeQuery = true)
    Iterable<OldPassword> findAllByUserIdTop3OldPassword(@PathVariable Long id);
}
