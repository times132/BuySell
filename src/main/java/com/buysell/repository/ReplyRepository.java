package com.buysell.repository;

import com.buysell.domain.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query(
            value = "select r from Reply r join fetch r.user join fetch r.board where r.board.bid=:bid",
            countQuery = "select count(r) from Reply r")
    Page<Reply> findAllByBoardBid(Long bid, Pageable pageable);

    @Query(value = "select board_bid from replys where rid=?1", nativeQuery=true)
    Long findBidByRid(Long rid);
    Long countByBoardBid(Long bid);
}
