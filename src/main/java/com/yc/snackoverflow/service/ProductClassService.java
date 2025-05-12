package com.yc.snackoverflow.service;

import com.yc.snackoverflow.data.ProductClassDto;

import java.util.List;

public interface ProductClassService {
    void create(List<String> nameList);

    List<String> list(List<String> nameList, Boolean isAlive);

    void updateAlive(List<ProductClassDto> productClassDtos);
}
