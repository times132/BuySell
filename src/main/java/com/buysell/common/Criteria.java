package com.buysell.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;

@Setter
@Getter
public class Criteria {

    private Integer page; // 현재 페이지
    private Integer pageSize; // 한 화면에 보이는 개수

    public Criteria(){
        this(1, 5);
    }

    public Criteria(Integer page, Integer pageSize){
        this.page = page;
        this.pageSize = pageSize;
    }

    public String makeUrl(Integer page){
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("page", page);

        return builder.toUriString();
    }
}
