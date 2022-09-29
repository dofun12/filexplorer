package com.example.filexplorer.repository;

import com.example.filexplorer.model.FileRegisterModel;
import com.example.filexplorer.model.FileTagsModel;
import com.example.filexplorer.model.FileTagsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRegisterRepository extends JpaRepository<FileRegisterModel, String> {
}
