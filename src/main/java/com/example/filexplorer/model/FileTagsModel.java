package com.example.filexplorer.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "filetags")
@IdClass(FileTagsPK.class)
public class FileTagsModel implements Serializable {

    @Id
    @Column
    private String fileuuid;

    @Id
    @Column(name = "property_name")
    private String name;

    @Column(name = "property_value")
    private String value;

    @Column(name = "type")
    private String type;

    public FileTagsModel(FileTagsPK fileTagsPK, String value, String type) {
        this.fileuuid = fileTagsPK.getFileuuid();
        this.name = fileTagsPK.getName();
        this.value = value;
        this.type = type;
    }


    public FileTagsModel(String fileuuid, String key, String value, String type) {
        this.fileuuid = fileuuid;
        this.name = key;
        this.value = value;
        this.type = type;
    }

    public FileTagsModel() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileTagsModel that = (FileTagsModel) o;
        return Objects.equals(fileuuid, that.fileuuid) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileuuid, name);
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

