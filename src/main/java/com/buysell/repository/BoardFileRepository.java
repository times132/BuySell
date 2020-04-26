package com.buysell.repository;

import com.buysell.domain.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFile, String> {

    List<BoardFile> findAllByBoardBid(Long bid);
    void deleteByUuid(String uuid);
}
