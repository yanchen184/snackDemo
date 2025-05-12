package com.yc.snackoverflow.mapper;

import com.yc.snackoverflow.dto.request.ProductCreateRequestDto;
import com.yc.snackoverflow.dto.response.ProductResponseDto;
import com.yc.snackoverflow.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper for Product entity and DTOs
 */
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {ListMapper.class}
)
public interface ProductMapper {

    /**
     * Convert Product entity to ProductResponseDto
     * 
     * @param product The product entity
     * @return ProductResponseDto
     */
    @Mapping(target = "productClassId", source = "productClass.id")
    @Mapping(target = "productClassName", source = "productClass.name")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ProductResponseDto toResponseDto(Product product);

    /**
     * Convert ProductCreateRequestDto to Product entity
     * 
     * @param requestDto The create request dto
     * @return Product entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productClass", ignore = true)
    @Mapping(target = "productCommits", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "alive", constant = "true")
    Product toEntity(ProductCreateRequestDto requestDto);

    /**
     * Update Product entity from DTO
     * 
     * @param product The product entity to update
     * @param requestDto The data to update with
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productClass", ignore = true)
    @Mapping(target = "productCommits", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateProductFromDto(ProductCreateRequestDto requestDto, @MappingTarget Product product);
}
