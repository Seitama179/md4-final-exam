package com.example.demo.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IGenerateService<T> {

    List<T> findAll();

    Optional<T> findById(Long id);
}
