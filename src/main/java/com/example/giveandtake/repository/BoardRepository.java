package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Board> findAllByWriter(String writer, Pageable pageable);
}
