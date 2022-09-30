package com.example.filexplorer.model;


import javax.persistence.*;

@Entity
@Table(name = "directoryregister")
public class DirectoryModel {
    @Id
    @Column(name = "diruuid")
    private String diruuid;

    @Lob
    @Column(name = "pathEncoded")
    private String pathEncoded;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiruuid() {
        return diruuid;
    }

    public void setDiruuid(String diruuid) {
        this.diruuid = diruuid;
    }

    public String getPathEncoded() {
        return pathEncoded;
    }

    public void setPathEncoded(String pathEncoded) {
        this.pathEncoded = pathEncoded;
    }
}

