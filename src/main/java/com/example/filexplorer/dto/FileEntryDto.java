package com.example.filexplorer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.stream.Collectors;

public class FileEntryDto {
    private String name;
    private String fileuuid;
    private String pathencoded;
    private String parentFileuuid;
    private List<FileEntryTagDto> tags;

    public FileEntryTagDto getTag(final String name) {
        if (tags == null) return FileEntryTagDto.empty;
        Optional<FileEntryTagDto> optTagDto = tags.stream().filter(fileEntryTagDto -> Objects.equals(fileEntryTagDto.getName(), name)).findFirst();
        return optTagDto.orElse(FileEntryTagDto.empty);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FileEntryTagDto> getTags(String[] fields) {
        if (fields == null) return new ArrayList<>();
        if (this.getTags() == null) return new ArrayList<>();
        return this.getTags().stream().filter(fileEntryTagDto -> Arrays.stream(fields).anyMatch(field -> Objects.equals(field,fileEntryTagDto.getName()))).collect(Collectors.toList());
    }

    public FileEntryDto() {
    }

    public String getParentFileuuid() {
        return parentFileuuid;
    }

    public void setParentFileuuid(String parentFileuuid) {
        this.parentFileuuid = parentFileuuid;
    }

    public FileEntryDto(String fileuuid, String pathencoded) {
        this.fileuuid = fileuuid;
        this.pathencoded = pathencoded;
    }

    public FileEntryDto(String fileuuid, String pathencoded, List<FileEntryTagDto> tags) {
        this.fileuuid = fileuuid;
        this.pathencoded = pathencoded;
        this.tags = tags;
    }

    public void addTag(FileEntryTagDto tag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tag);
    }

    public String getFileuuid() {
        return fileuuid;
    }

    public void setFileuuid(String fileuuid) {
        this.fileuuid = fileuuid;
    }

    public String getPathencoded() {
        return pathencoded;
    }

    public void setPathencoded(String pathencoded) {
        this.pathencoded = pathencoded;
    }

    public List<FileEntryTagDto> getTags() {
        return tags;
    }

    public void setTags(List<FileEntryTagDto> tags) {
        this.tags = tags;
    }

}
