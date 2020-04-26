package com.buysell.domain.DTO;

import com.buysell.domain.entity.Board;
import com.buysell.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeDTO {


    private Long id;

    private User user;

    private Board board;

}
