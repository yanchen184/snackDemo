package com.yc.snackoverflow.controller;

import com.yc.snackoverflow.constant.ApiPath;
import com.yc.snackoverflow.dto.request.ProductCreateRequestDto;
import com.yc.snackoverflow.dto.request.ProductUpdateRequestDto;
import com.yc.snackoverflow.dto.response.ProductResponseDto;
import com.yc.snackoverflow.handler.PageResult;
import com.yc.snackoverflow.handler.ResultData;
import com.yc.snackoverflow.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product controller for managing products
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPath.PRODUCTS)
@Tag(name = "Product Management", description = "API endpoints for product management")
public class ProductController {

    private final ProductService productService;

    /**
     * Get a paginated list of products
     */
    @Operation(summary = "Get products", description = "Get a paginated list of products with optional filtering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", 
                        content = @Content(schema = @Schema(implementation = ResultData.class)))
    })
    @GetMapping
    public ResultData<PageResult<ProductResponseDto>> getProducts(
            @Parameter(description = "Product name for filtering") 
            @RequestParam(required = false) String name,
            
            @Parameter(description = "Product class ID for filtering") 
            @RequestParam(required = false) Long classId,
            
            @Parameter(description = "Page number (0-based)") 
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "Page size") 
            @RequestParam(defaultValue = "10") int size,
            
            @Parameter(description = "Sort field and direction (e.g. name,asc)") 
            @RequestParam(defaultValue = "id,desc") String sort) {
        
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && "asc".equalsIgnoreCase(sortParams[1]) ? 
                Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        
        Page<ProductResponseDto> products = productService.findProducts(name, classId, pageRequest);
        return ResultData.success(PageResult.from(products));
    }

    /**
     * Get a product by ID
     */
    @Operation(summary = "Get product by ID", description = "Get a specific product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", 
                        content = @Content(schema = @Schema(implementation = ResultData.class)))
    })
    @GetMapping("/{id}")
    public ResultData<ProductResponseDto> getProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        
        ProductResponseDto product = productService.findById(id);
        return ResultData.success(product);
    }

    /**
     * Create a new product
     */
    @Operation(summary = "Create product", description = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", 
                        content = @Content(schema = @Schema(implementation = ResultData.class)))
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResultData<ProductResponseDto> createProduct(
            @Valid @RequestBody ProductCreateRequestDto request) {
        
        log.info("Creating new product: {}", request.getName());
        ProductResponseDto createdProduct = productService.createProduct(request);
        return ResultData.success("Product created successfully", createdProduct);
    }

    /**
     * Create multiple products
     */
    @Operation(summary = "Create multiple products", description = "Create multiple products at once")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products created successfully", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", 
                        content = @Content(schema = @Schema(implementation = ResultData.class)))
    })
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResultData<List<ProductResponseDto>> createProducts(
            @Valid @RequestBody List<ProductCreateRequestDto> requests) {
        
        log.info("Batch creating {} products", requests.size());
        List<ProductResponseDto> createdProducts = productService.createProducts(requests);
        return ResultData.success("Products created successfully", createdProducts);
    }

    /**
     * Update an existing product
     */
    @Operation(summary = "Update product", description = "Update an existing product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", 
                        content = @Content(schema = @Schema(implementation = ResultData.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResultData<ProductResponseDto> updateProduct(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequestDto request) {
        
        log.info("Updating product with ID: {}", id);
        ProductResponseDto updatedProduct = productService.updateProduct(id, request);
        return ResultData.success("Product updated successfully", updatedProduct);
    }

    /**
     * Delete a product
     */
    @Operation(summary = "Delete product", description = "Delete a product by ID (logical deletion)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", 
                        content = @Content(schema = @Schema(implementation = ResultData.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", 
                        content = @Content(schema = @Schema(implementation = ResultData.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResultData<Void> deleteProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        
        log.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
        return ResultData.success("Product deleted successfully");
    }
}
