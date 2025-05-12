package com.yc.snackoverflow.controller;


import com.yc.snackoverflow.data.ProductClassDto;
import com.yc.snackoverflow.service.ProductClassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("productsClass")
public class ProductClassController {


    private final ProductClassService productClassService;

    @PutMapping
    public void create(@Validated @RequestBody List<String> productDtos) {
        productClassService.create(productDtos);
    }

    @GetMapping
    public List<String> list(@RequestParam(required = false) List<String> products, @RequestParam(required = false) Boolean isAlive) {
        return productClassService.list(products, isAlive);
    }

    @PostMapping
    public void updateAlive(@Validated @RequestBody List<ProductClassDto> productDtos) {
        productClassService.updateAlive(productDtos);
    }
}
