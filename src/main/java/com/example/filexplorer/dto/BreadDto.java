package com.example.filexplorer.dto;

public class BreadDto {
    private String name;
    private String link;
    private Boolean active;

    public BreadDto() {
    }

    public Boolean getActive() {
        return active;
    }

    public BreadDto(String name, String link, Boolean active) {
        this.name = name;
        this.link = link;
        this.active = active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BreadDto(String name, String link) {
        this.name = name;
        this.link = link;
        this.active = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
