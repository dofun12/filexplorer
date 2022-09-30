package com.example.filexplorer.dto;

import com.example.filexplorer.model.FileTagsModel;

public class FileEntryTagDto {
    private String name;
    private String value;
    private String type;

    public FileEntryTagDto() {
    }

    final public static FileEntryTagDto empty = new FileEntryTagDto("","","");

    public FileEntryTagDto(FileTagsModel tagModel) {
        if(tagModel==null) return;
        this.name = tagModel.getName();
        this.type = tagModel.getType();
        this.value = tagModel.getValue();
    }

    public FileEntryTagDto(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
