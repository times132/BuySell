package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Page<Reply> findAllByBid(Long bid, Pageable pageable);
}
