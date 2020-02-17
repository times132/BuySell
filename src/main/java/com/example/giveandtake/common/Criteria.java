package com.example.giveandtake.common;

import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
public class Criteria {

    private Integer curPage;
    private Integer amount;

    private String type;
    private String keyword;

    public Criteria(){
        this(1);
    }

    public Criteria(Integer curPage){
        this.curPage = curPage;
    }

    public String[] getTypeArr(){
        return type == null ? new String[] {} : type.split("");
    }

    public String getKeyword(){
        if (keyword == null){
            return "";
        }else{
            return this.keyword;
        }
    }

    public String getUrlLink(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("page", getCurPage())
                .queryParam("amount", getAmount())
                .queryParam("type", getType())
                .queryParam("keyword", getKeyword());

        return builder.toUriString();
    }
}
