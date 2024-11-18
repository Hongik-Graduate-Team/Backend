package com.example.Namanba.Interview.dto.request;

import org.springframework.data.domain.Sort;

public class InterviewPageRequest {
    private int page = 1; // 기본값 1
    private final int size = 10;
    private Sort.Direction direction = Sort.Direction.DESC; // 기본 정렬 내림차순

    public void setPage(int page){
        this.page = page <= 0 ? 1 : page;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = (direction != null) ? direction : Sort.Direction.DESC;
    }

    public org.springframework.data.domain.PageRequest of(){
        return org.springframework.data.domain.PageRequest.of(page -1, size, direction, "createdDate");
    }
}
