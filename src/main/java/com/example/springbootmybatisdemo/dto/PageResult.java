package com.example.springbootmybatisdemo.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({
        "page",
        "size",
        "total",
        "totalPages",
        "hasPrevious",
        "hasNext",
        "empty",
        "records"
})
public class PageResult<T> {

    private final int page;
    private final int size;
    private final long total;
    private final long totalPages;
    private final boolean hasPrevious;
    private final boolean hasNext;
    private final boolean empty;
    private final List<T> records;

    public PageResult(int page, int size, long total, List<T> records) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = (total + size - 1) / size;
        this.hasPrevious = page > 1;
        this.hasNext = page < totalPages;
        this.empty = records.isEmpty();
        this.records = records;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isEmpty() {
        return empty;
    }

    public List<T> getRecords() {
        return records;
    }
}
