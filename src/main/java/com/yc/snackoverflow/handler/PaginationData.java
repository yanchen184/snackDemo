package com.yc.snackoverflow.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationData {

    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}
