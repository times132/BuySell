package com.example.giveandtake.common;

import lombok.Getter;

@Getter
public class Pagination {

    private Integer listSize; // 밑에 보이는 페이지 개수
    private Integer rangeSize; // 한 페이지에 보이는 게시물 개수
    private Integer curPage; // 현재 페이지
    private double totalCnt; // 총 개수
    private Integer startPage; // 현재 블럭 시작 페이지 번호
    private Integer endPage; // 끝 페이지 번호
    private boolean prev, next; // 이전, 다음 버튼 유뮤

    public Pagination(Integer curPage, double totalCnt, Integer listSize, Integer rangeSize){
        this.curPage = curPage;
        this.totalCnt = totalCnt;
        this.listSize = listSize;
        this.rangeSize = rangeSize;

        this.endPage = (int) Math.ceil(curPage / (double)listSize) * listSize; // 총게시물/밑에 보이는 게시물로 끝 페이지 번호 구함
        this.startPage = this.endPage - listSize +1;

        int realEndPage = (int) Math.ceil(totalCnt / rangeSize); // 현재 블럭 끝 페이지 번호

        if (endPage > realEndPage){ // 현재 블럭 끝페이지 번호가 실제 페이지를 넘어가는거 방지
            this.endPage = realEndPage;
        }

        this.prev = this.startPage > 1;
        this.next = this.endPage < realEndPage;
    }
}
