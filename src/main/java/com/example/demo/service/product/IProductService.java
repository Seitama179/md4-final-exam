package com.example.demo.service.product;

import com.example.demo.model.Product;
import com.example.demo.service.IGenerateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService extends IGenerateService<Product> {
    Page<Product> findAll(Pageable pageable);
}
