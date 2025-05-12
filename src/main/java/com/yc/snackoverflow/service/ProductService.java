package com.yc.snackoverflow.service;

import com.yc.snackoverflow.dto.request.ProductCreateRequestDto;
import com.yc.snackoverflow.dto.request.ProductUpdateRequestDto;
import com.yc.snackoverflow.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing products
 */
public interface ProductService {

    /**
     * Find products with optional filtering and pagination
     *
     * @param name Product name for filtering (optional)
     * @param classId Product class ID for filtering (optional)
     * @param pageable Pagination information
     * @return Page of products
     */
    Page<ProductResponseDto> findProducts(String name, Long classId, Pageable pageable);

    /**
     * Find a product by ID
     *
     * @param id Product ID
     * @return Product DTO
     */
    ProductResponseDto findById(Long id);

    /**
     * Create a new product
     *
     * @param requestDto Product creation request
     * @return Created product
     */
    ProductResponseDto createProduct(ProductCreateRequestDto requestDto);

    /**
     * Create multiple products
     *
     * @param requestDtos List of product creation requests
     * @return List of created products
     */
    List<ProductResponseDto> createProducts(List<ProductCreateRequestDto> requestDtos);

    /**
     * Update an existing product
     *
     * @param id Product ID
     * @param requestDto Product update request
     * @return Updated product
     */
    ProductResponseDto updateProduct(Long id, ProductUpdateRequestDto requestDto);

    /**
     * Delete a product (logical deletion)
     *
     * @param id Product ID
     */
    void deleteProduct(Long id);

    /**
     * Legacy method: Get products by name list
     *
     * @param productNameList List of product names
     * @return List of products
     */
    List<ProductResponseDto> findByNameList(List<String> productNameList);
}
