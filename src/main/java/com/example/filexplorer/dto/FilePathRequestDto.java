package com.example.filexplorer.dto;

public class FilePathRequestDto {
    private String path;

    public FilePathRequestDto() {
    }

    public FilePathRequestDto(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
