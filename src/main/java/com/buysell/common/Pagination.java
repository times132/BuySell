package com.buysell.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pagination {

    private double total; // 총 개수
    private Integer startPage; // 현재 블럭 시작 페이지 번호
    private Integer endPage; // 현재 블럭 끝 페이지 번호
    private Integer realEndPage; // 전체 마지막 페이지 번호
    private boolean prev, next; // 이전, 다음 버튼 유뮤
    private Criteria cri;

    @Builder
    public Pagination(Criteria cri, Long total, Integer realEndPage, Integer listSize){
        this.cri = cri;
        this.total = total;
        this.realEndPage = realEndPage;

        this.endPage = (int) Math.ceil(cri.getPage() / (double)listSize) * listSize;
        this.startPage = endPage - listSize + 1;

        if (this.realEndPage < this.endPage) this.endPage = this.realEndPage;

        this.prev = startPage > 1;
        this.next = this.endPage < this.realEndPage;
    }


}
