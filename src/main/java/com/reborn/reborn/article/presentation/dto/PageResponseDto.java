package com.reborn.reborn.article.presentation.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDto<T> {
    private List<T> pageList;
    private int totalPage;
    private int page;
    private int size;
    private int start;
    private int end;
    private boolean prev;
    private boolean next;
    private List<Integer> pageNumberList;

    public PageResponseDto(Page<T> result) {
        pageList = result.getContent();
        totalPage = result.getTotalPages();
        getPageNumberList(result.getPageable());
    }

    private void getPageNumberList(Pageable pageable) {
        page = pageable.getPageNumber() + 1;
        size = pageable.getPageSize();
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        start = tempEnd - 9;
        end = Math.min(totalPage, tempEnd);
        prev = pageable.hasPrevious();
        next = totalPage > tempEnd;
        pageNumberList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}