package com.buysell.repository;

import com.buysell.domain.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Board> findAllByWriter(String writer, Pageable pageable);
    Page<Board> findAllByUserId(Long id, Pageable pageable);
    Page<Board> findAllByCategory(String categpry, Pageable pageable);
}
