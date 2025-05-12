package com.yc.snackoverflow.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Page result wrapper for API responses
 *
 * @param <T> Type of the page items
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private boolean first;
    private boolean last;
    private boolean empty;

    /**
     * Create from Spring Data Page
     *
     * @param page Spring Data Page
     * @param <T> Type of the page items
     * @return PageResult instance
     */
    public static <T> PageResult<T> from(Page<T> page) {
        return new PageResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isFirst(),
                page.isLast(),
                page.isEmpty()
        );
    }
}
