package com.example.giveandtake.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;

@Setter
@Getter
public class SearchCriteria extends Criteria{

    private String type = "";
    private String keyword = "";

    public SearchCriteria(){
        super(1, 5);
    }

    public String getType(){
        if (this.type == null){
            return "";
        }else{
            return this.type;
        }
    }

    public String makeSearchUrl(Integer page){
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("page", page)
                .queryParam("type", (getType()))
                .queryParam("keyword", (getKeyword()));

        return builder.toUriString();
    }
}
