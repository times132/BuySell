package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFile, String> {

    List<BoardFile> findAllByBoardBid(Long bid);
}
