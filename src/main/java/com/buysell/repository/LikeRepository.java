package com.buysell.repository;

import com.buysell.domain.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByUserIdAndBoardBid(Long user_id, Long bid);
    Page<Like> findAllByUserId(Long user_id, Pageable pageable);
}
