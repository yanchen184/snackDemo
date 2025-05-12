package com.yc.snackoverflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for product responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Long id;
    private String name;
    private Integer price;
    private String picture;
    private Boolean alive;
    private Long productClassId;
    private String productClassName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
