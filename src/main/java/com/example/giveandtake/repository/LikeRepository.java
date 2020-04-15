package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.Like;
import com.example.giveandtake.model.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByUserIdAndBoardBid(Long user_id, Long bid);
    Page<Like> findAllByUserId(Long user_id, Pageable pageable);
}
