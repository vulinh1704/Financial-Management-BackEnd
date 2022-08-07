package com.example.airbnb.service.impl;

import com.example.airbnb.model.OldPassword;
import com.example.airbnb.repository.OldPasswordRepository;
import com.example.airbnb.service.OldPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OldPasswordImpl implements OldPasswordService {
    @Autowired
    OldPasswordRepository oldPasswordRepository;

    @Override
    public void save(OldPassword oldPassword) {
        oldPasswordRepository.save(oldPassword);
    }

    @Override
    public Iterable<OldPassword> findAll() {
        return oldPasswordRepository.findAll();
    }

    @Override
    public Optional<OldPassword> findById(Long id) {
        return oldPasswordRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        oldPasswordRepository.deleteById(id);
    }

    @Override
    public Iterable<OldPassword> findAllByUserIdTop3OldPassword(Long id) {
        return oldPasswordRepository.findAllByUserIdTop3OldPassword(id);
    }
}
