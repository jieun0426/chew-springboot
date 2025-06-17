package com.example.chew.Detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PageDTO {
    int total; //총 게시물 수
    int nowPage; //현재 페이지
    int cntPerPage; //페이지당 게시물 수

    public int getStart() {
        return (nowPage - 1) * cntPerPage;
    }

    public int getEnd() {
        return nowPage * cntPerPage;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) total / cntPerPage);
    }



}
