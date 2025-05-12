package com.yc.snackoverflow.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResultData<T> {

    private List<T> content;

    private PaginationData pagination;

    private static <T> PaginationData buildPaginationData(Page<T> page) {
        return PaginationData.builder()
                .currentPage(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public static <T, E> PaginationResultData<T> convertToPaginationData(Page<E> page, List<T> content) {
        return PaginationResultData.<T>builder()
                .content(content)
                .pagination(buildPaginationData(page)).build();
    }

    public static <T, E> PaginationResultData<T> convertToPaginationData(Page<E> page, Function<E, T> func) {
        return PaginationResultData.<T>builder()
                .content(page.getContent().stream().map(func).collect(Collectors.toList()))
                .pagination(buildPaginationData(page)).build();
    }
}
