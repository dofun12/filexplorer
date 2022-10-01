package com.example.filexplorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class FileUtils {
    public static ArrayList<File> getFiles(File dir){
        ArrayList<File> files = new ArrayList<>();
        if(dir==null ||dir.listFiles()==null){
            return files;
        }
        files.addAll(Arrays.asList(dir.listFiles()));
        return files;
    }
    public static ArrayList<File> getFilesRecursive(File dir){
        return getFilesRecursive(dir,null);
    }
    public static ArrayList<File> getFilesRecursive(File dir, OnFileFounded onFileFounded){
        ArrayList<File> files = new ArrayList<>();
        if(dir==null ||dir.listFiles()==null){
            return files;
        }
        for(File subFile:dir.listFiles()){
            if(subFile.isDirectory()){
                files.addAll(getFiles(subFile));
            }
            if(onFileFounded!=null){
                onFileFounded.founded(subFile);
            }

            files.add(subFile);
        }
        return files;
    }

}
