package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByUserIdAndBoardBid(Long user_id, Long bid);
}
