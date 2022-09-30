package com.example.filexplorer.repository;

import com.example.filexplorer.model.FileRegisterModel;
import com.example.filexplorer.model.FileTagsModel;
import com.example.filexplorer.model.FileTagsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRegisterRepository extends JpaRepository<FileRegisterModel, String> {
    List<FileRegisterModel> findAllByParentfileuuidIsNull();
    List<FileRegisterModel> findAllByFileuuidAndParentfileuuidIsNull(String fileuuid);
    List<FileRegisterModel> findAllByParentfileuuid(String parentfileuuid);
}
