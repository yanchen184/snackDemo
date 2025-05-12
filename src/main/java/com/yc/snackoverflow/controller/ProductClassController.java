package com.yc.snackoverflow.controller;

import com.yc.snackoverflow.data.ProductClassDto;
import com.yc.snackoverflow.handler.ResultData;
import com.yc.snackoverflow.service.ProductClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/api/product-classes")
@Tag(name = "Product Class Management", description = "API endpoints for product class management")
public class ProductClassController {


    private final ProductClassService productClassService;

    /**
     * Create product classes in batch
     */
    @Operation(summary = "Create product classes", description = "Create multiple product classes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product classes created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResultData<Void> createProductClasses(@Validated @RequestBody List<String> productClassNames) {
        log.info("Creating {} product classes", productClassNames.size());
        productClassService.create(productClassNames);
        return ResultData.success("Product classes created successfully", null);
    }

    /**
     * Get product classes with optional filtering
     */
    @Operation(summary = "Get product classes", description = "Get product classes with optional filtering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    public ResultData<List<String>> getProductClasses(
            @Parameter(description = "List of product class names for filtering")
            @RequestParam(required = false) List<String> names,
            @Parameter(description = "Filter by active status (true/false)")
            @RequestParam(required = false) Boolean isAlive) {
        log.info("Getting product classes, filter by names: {}, isAlive: {}", names, isAlive);
        List<String> productClasses = productClassService.list(names, isAlive);
        return ResultData.success(productClasses);
    }

    /**
     * Update product class status (active/inactive)
     */
    @Operation(summary = "Update product class status", description = "Update active status of product classes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product classes updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "One or more product classes not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResultData<Void> updateProductClassStatus(@Validated @RequestBody List<ProductClassDto> productClassDtos) {
        log.info("Updating status for {} product classes", productClassDtos.size());
        productClassService.updateAlive(productClassDtos);
        return ResultData.success("Product class status updated successfully", null);
    }
}
