package com.example.filexplorer.model;


import javax.persistence.*;

@Entity
@Table(name = "fileregister")
public class FileRegisterModel {
    @Id
    @Column(name = "fileuuid")
    private String fileuuid;

    @Column(name = "parent_fileuuid")
    private String parentfileuuid;

    @Lob
    @Column(name = "pathEncoded")
    private String pathEncoded;

    @Column
    private String alias;

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentfileuuid() {
        return parentfileuuid;
    }

    public void setParentfileuuid(String parentfileuuid) {
        this.parentfileuuid = parentfileuuid;
    }

    public String getPathEncoded() {
        return pathEncoded;
    }

    public void setPathEncoded(String pathEncoded) {
        this.pathEncoded = pathEncoded;
    }

    public String getFileuuid() {
        return fileuuid;
    }

    public void setFileuuid(String fileuuid) {
        this.fileuuid = fileuuid;
    }
}

