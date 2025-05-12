package com.yc.snackoverflow.service.impl;

import com.yc.snackoverflow.constant.ErrorCode;
import com.yc.snackoverflow.dto.request.ProductCreateRequestDto;
import com.yc.snackoverflow.dto.request.ProductUpdateRequestDto;
import com.yc.snackoverflow.dto.response.ProductResponseDto;
import com.yc.snackoverflow.exception.WebErrorEnum;
import com.yc.snackoverflow.exception.WebException;
import com.yc.snackoverflow.mapper.ProductMapper;
import com.yc.snackoverflow.model.Product;
import com.yc.snackoverflow.model.ProductClass;
import com.yc.snackoverflow.repository.ProductClassDao;
import com.yc.snackoverflow.repository.ProductDao;
import com.yc.snackoverflow.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ProductService interface
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ProductClassDao productClassDao;
    private final ProductMapper productMapper;

    @Override
    @Cacheable(value = "products", key = "'page:' + #name + ':' + #classId + ':' + #pageable.pageNumber + ':' + #pageable.pageSize")
    public Page<ProductResponseDto> findProducts(String name, Long classId, Pageable pageable) {
        Page<Product> productPage;

        if (StringUtils.hasText(name) && classId != null) {
            // Both name and classId are provided
            productPage = productDao.findByNameContainingAndProductClassIdAndAliveTrue(name, classId, pageable);
        } else if (StringUtils.hasText(name)) {
            // Only name is provided
            productPage = productDao.findByNameContainingAndAliveTrue(name, pageable);
        } else if (classId != null) {
            // Only classId is provided
            productPage = productDao.findByProductClassIdAndAliveTrue(classId, pageable);
        } else {
            // No filters
            productPage = productDao.findByAliveTrue(pageable);
        }

        return productPage.map(productMapper::toResponseDto);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductResponseDto findById(Long id) {
        Product product = productDao.findById(id)
                .orElseThrow(() -> new WebException(WebErrorEnum.PRODUCT_NOT_FOUND));
        return productMapper.toResponseDto(product);
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto) {
        // Validate product class exists
        ProductClass productClass = productClassDao.findById(requestDto.getProductClassId())
                .orElseThrow(() -> new WebException(WebErrorEnum.PRODUCT_CLASS_NOT_FOUND));

        // Check if product name already exists
        if (productDao.existsByName(requestDto.getName())) {
            throw new WebException(ErrorCode.PRODUCT_CREATE_FAILED, "Product with name '" + requestDto.getName() + "' already exists");
        }

        // Create new product
        Product product = new Product();
        product.setName(requestDto.getName());
        product.setPicture(requestDto.getPicture());
        product.setPrice(requestDto.getPrice());
        product.setAlive(true);
        product.setProductClass(productClass);

        // Save and return
        Product savedProduct = productDao.save(product);
        log.info("Created new product with ID: {}", savedProduct.getId());

        return productMapper.toResponseDto(savedProduct);
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public List<ProductResponseDto> createProducts(List<ProductCreateRequestDto> requestDtos) {
        List<Product> products = new ArrayList<>();

        for (ProductCreateRequestDto requestDto : requestDtos) {
            // Validate product class exists
            ProductClass productClass = productClassDao.findById(requestDto.getProductClassId())
                    .orElseThrow(() -> new WebException(WebErrorEnum.PRODUCT_CLASS_NOT_FOUND));

            // Check if product name already exists
            if (productDao.existsByName(requestDto.getName())) {
                // Skip this product and log warning
                log.warn("Product with name '{}' already exists, skipping", requestDto.getName());
                continue;
            }

            // Create new product
            Product product = new Product();
            product.setName(requestDto.getName());
            product.setPicture(requestDto.getPicture());
            product.setPrice(requestDto.getPrice());
            product.setAlive(true);
            product.setProductClass(productClass);

            products.add(product);
        }

        // Save all and return
        List<Product> savedProducts = productDao.saveAll(products);
        log.info("Created {} new products", savedProducts.size());

        return savedProducts.stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", key = "#id")
    public ProductResponseDto updateProduct(Long id, ProductUpdateRequestDto requestDto) {
        // Find product
        Product product = productDao.findById(id)
                .orElseThrow(() -> new WebException(WebErrorEnum.PRODUCT_NOT_FOUND));

        // Update fields if provided
        if (StringUtils.hasText(requestDto.getName()) && !product.getName().equals(requestDto.getName())) {
            // Check if new name already exists
            if (productDao.existsByNameAndIdNot(requestDto.getName(), id)) {
                throw new WebException(ErrorCode.PRODUCT_UPDATE_FAILED, "Product with name '" + requestDto.getName() + "' already exists");
            }
            product.setName(requestDto.getName());
        }

        if (StringUtils.hasText(requestDto.getPicture())) {
            product.setPicture(requestDto.getPicture());
        }

        if (requestDto.getPrice() != null && requestDto.getPrice() >= 0) {
            product.setPrice(requestDto.getPrice());
        }

        if (requestDto.getAlive() != null) {
            product.setAlive(requestDto.getAlive());
        }

        if (requestDto.getProductClassId() != null) {
            ProductClass productClass = productClassDao.findById(requestDto.getProductClassId())
                    .orElseThrow(() -> new WebException(WebErrorEnum.PRODUCT_CLASS_NOT_FOUND));
            product.setProductClass(productClass);
        }

        // Save and return
        Product updatedProduct = productDao.save(product);
        log.info("Updated product with ID: {}", updatedProduct.getId());

        return productMapper.toResponseDto(updatedProduct);
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(Long id) {
        // Find product
        Product product = productDao.findById(id)
                .orElseThrow(() -> new WebException(WebErrorEnum.PRODUCT_NOT_FOUND));

        // Logical deletion
        product.setAlive(false);
        productDao.save(product);
        log.info("Deleted product with ID: {}", id);
    }

    @Override
    public List<ProductResponseDto> findByNameList(List<String> productNameList) {
        if (productNameList == null || productNameList.isEmpty()) {
            return productDao.findByAliveTrue()
                    .stream()
                    .map(productMapper::toResponseDto)
                    .collect(Collectors.toList());
        }

        List<Product> products = productDao.findByNameInAndAliveTrue(productNameList);
        if (products.isEmpty()) {
            throw new WebException(WebErrorEnum.PRODUCT_NOT_FOUND);
        }

        return products.stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> list(List<String> productNameList) {
        List<Product> products = productDao.list(productNameList);
        if (products.isEmpty()) {
            throw new WebException(WebErrorEnum.PRODUCT_NOT_FOUND);
        }
        return products;
    }

    @Override
    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public void createOrUpdate(List<com.yc.snackoverflow.data.ProductDto> productDtos) {
        if (productDtos == null || productDtos.isEmpty()) {
            log.warn("No products provided for create or update operation");
            return;
        }

        for (com.yc.snackoverflow.data.ProductDto dto : productDtos) {
            Product product;

            // Check if product exists
            boolean exists = productDao.existsByName(dto.getName());

            if (exists) {
                // Find the existing product
                List<String> nameList = new ArrayList<>();
                nameList.add(dto.getName());
                List<Product> products = productDao.findByNameInAndAliveTrue(nameList);

                if (products.isEmpty()) {
                    // Product exists but is not active, create new one
                    product = new Product();
                    product.setName(dto.getName());
                    product.setAlive(true);
                    log.info("Creating new product with name: {}", dto.getName());
                } else {
                    // Update existing product
                    product = products.get(0);
                    log.info("Updating existing product with name: {}", dto.getName());
                }
            } else {
                // Create new product
                product = new Product();
                product.setName(dto.getName());
                product.setAlive(true);
                log.info("Creating new product with name: {}", dto.getName());
            }

            // Update fields
            product.setPrice(dto.getPrice());

            if (dto.getPicture() != null) {
                product.setPicture(dto.getPicture());
            }

            if (dto.getAlive() != null) {
                product.setAlive(dto.getAlive());
            }

            // Save product
            productDao.save(product);
        }

        log.info("Processed {} products for create or update", productDtos.size());
    }
}
