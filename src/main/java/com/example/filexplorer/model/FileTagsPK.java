package com.example.filexplorer.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


public class FileTagsPK implements Serializable {
    @Id
    @Column
    private String  fileuuid;

    @Id
    @Column
    private String filepropertyname;

    public FileTagsPK() {
    }

    public FileTagsPK(String fileuuid, String key) {
        this.fileuuid = fileuuid;
        this.filepropertyname = key;
    }

    public String getFileuuid() {
        return fileuuid;
    }

    public void setFileuuid(String fileuuid) {
        this.fileuuid = fileuuid;
    }

    public String getFilepropertyname() {
        return filepropertyname;
    }

    public void setFilepropertyname(String filepropertyname) {
        this.filepropertyname = filepropertyname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileTagsPK that = (FileTagsPK) o;
        return Objects.equals(fileuuid, that.fileuuid) && Objects.equals(filepropertyname, that.filepropertyname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileuuid, filepropertyname);
    }
}

