package com.reborn.reborn.article.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Data
public class PageRequestDTO {

  private int page = 1;
  private int size = 10;
  private String sortBy = "regDate";
  private Sort.Direction direction = Sort.Direction.DESC;
  private final int DEFAULT_SIZE = 10;
  private final int MAX_SIZE = 50;

  public void setPage(int page) {
    this.page = Math.max(page, 1);
  }

  public void setSize(int size) {
    this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
  }

  public PageRequest of() {
    return PageRequest.of(page - 1, size, direction, sortBy);
  }

}