package com.example.filexplorer.model;


import javax.persistence.*;

@Entity
@Table(name = "fileregister")
public class FileRegisterModel {
    @Id
    @Column(name = "fileuuid")
    private String fileuuid;

    @Column(name = "pathEncoded")
    private String pathEncoded;

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

