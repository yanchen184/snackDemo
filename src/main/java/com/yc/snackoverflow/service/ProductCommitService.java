package com.yc.snackoverflow.service;

import com.yc.snackoverflow.data.ProductDto;
import com.yc.snackoverflow.enums.UpsertStatusEnum;
import com.yc.snackoverflow.model.Product;

import java.util.List;

public interface ProductCommitService {
    UpsertStatusEnum createOrUpdate(ProductDto productDto);

    List<Product> list(List<String> productNameList);
}
