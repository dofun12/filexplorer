package com.example.filexplorer.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


public class FileTagsPK implements Serializable {
    @Id
    @Column
    private String  fileuuid;

    @Id
    @Column(name = "property_name")
    private String name;

    public FileTagsPK() {
    }

    public FileTagsPK(String fileuuid, String key) {
        this.fileuuid = fileuuid;
        this.name = key;
    }

    public String getFileuuid() {
        return fileuuid;
    }

    public void setFileuuid(String fileuuid) {
        this.fileuuid = fileuuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String filepropertyname) {
        this.name = filepropertyname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileTagsPK that = (FileTagsPK) o;
        return Objects.equals(fileuuid, that.fileuuid) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileuuid, name);
    }
}

