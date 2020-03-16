package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Page<Reply> findAllByBoardBid(Long bid, Pageable pageable);
    @Query(value = "select board_id from replys where rid=?1", nativeQuery=true)
    Long findBidByRid(Long rid);
    Long countByBoardBid(Long bid);
}
