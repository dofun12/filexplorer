package com.example.filexplorer.repository;

import com.example.filexplorer.model.HistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryModel, Long> {
}
