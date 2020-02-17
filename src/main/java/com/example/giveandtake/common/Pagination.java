package com.example.giveandtake.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pagination {

    private Integer listSize; // 밑에 보이는 페이지 개수
    private Integer rangeSize; // 한 페이지에 보이는 게시물 개수

    private double total; // 총 개수
    private Integer startPage; // 현재 블럭 시작 페이지 번호
    private Integer endPage; // 끝 페이지 번호
    private boolean prev, next; // 이전, 다음 버튼 유뮤
    private Criteria cri;

    @Builder
    public Pagination(Criteria cri, Integer total){
        this.cri = cri;
        this.total = total;


    }
}
