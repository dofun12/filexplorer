package com.example.filexplorer.repository;

import com.example.filexplorer.model.FileTagsModel;
import com.example.filexplorer.model.FileTagsPK;
import com.example.filexplorer.model.HistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilesTagsRepository extends JpaRepository<FileTagsModel, FileTagsPK> {

    @Query("SELECT m FROM FileTagsModel m WHERE m.fileuuid = ?1 and m.filepropertyname = ?2")
    Optional<FileTagsModel> findByPk(String fileuuid, String key);
}
