package com.yc.snackoverflow.controller;

import com.yc.snackoverflow.data.ProductDto;
import com.yc.snackoverflow.handler.ResultData;
import com.yc.snackoverflow.model.Product;
import com.yc.snackoverflow.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/product-legacy")
@Tag(name = "Legacy Product API", description = "Legacy API endpoints for product management - use /api/products instead for new applications")
public class ProductCommitController {


    private final ProductService productService;

    /**
     * Create products in batch
     */
    @Operation(summary = "Create products", description = "Create multiple products (legacy API)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResultData<Void> createProducts(@Validated @RequestBody List<ProductDto> productDtos) {
        log.info("Creating {} products using legacy API", productDtos.size());
        productService.createOrUpdate(productDtos);
        return ResultData.success("Products created successfully", null);
    }

    /**
     * Update products in batch
     */
    @Operation(summary = "Update products", description = "Update multiple products (legacy API)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "One or more products not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResultData<Void> updateProducts(@Validated @RequestBody List<ProductDto> productDtos) {
        log.info("Updating {} products using legacy API", productDtos.size());
        productService.createOrUpdate(productDtos);
        return ResultData.success("Products updated successfully", null);
    }

    /**
     * Get products by name list
     */
    @Operation(summary = "Get products", description = "Get products filtered by name list (legacy API)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResultData<List<Product>> getProducts(
            @Parameter(description = "List of product names for filtering")
            @RequestParam(required = false) List<String> products) {
        log.info("Getting products with legacy API, filter by names: {}", products);
        List<Product> productList = productService.list(products);
        return ResultData.success(productList);
    }

    /**
     * Delete products in batch (logical deletion)
     */
    @Operation(summary = "Delete products", description = "Delete multiple products (logical deletion, legacy API)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResultData<Void> deleteProducts(@Validated @RequestBody List<ProductDto> productDtos) {
        log.info("Deleting {} products using legacy API", productDtos.size());
        
        // Set alive to false for all products to perform logical deletion
        productDtos.forEach(product -> product.setAlive(false));
        productService.createOrUpdate(productDtos);
        
        return ResultData.success("Products deleted successfully", null);
    }
}
