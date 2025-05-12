package com.yc.snackoverflow.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for product update requests
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequestDto {

    private String name;
    
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Integer price;
    
    private String picture;
    
    private Long productClassId;
    
    private Boolean alive;
}
