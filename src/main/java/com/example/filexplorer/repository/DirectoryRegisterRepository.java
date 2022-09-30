package com.example.filexplorer.repository;

import com.example.filexplorer.model.DirectoryModel;
import com.example.filexplorer.model.FileRegisterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRegisterRepository extends JpaRepository<DirectoryModel, String> {
}
