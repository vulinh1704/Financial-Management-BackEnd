package com.example.airbnb.service;

import com.example.airbnb.model.OldPassword;

import java.util.Optional;

public interface OldPasswordService {
    void save(OldPassword oldPassword);

    Iterable<OldPassword> findAll();

    Optional<OldPassword> findById(Long id);

    void remove(Long id);
    Iterable<OldPassword> findAllByUserIdTop3OldPassword(Long id);

}
