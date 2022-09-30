package com.example.filexplorer.service;

import com.example.filexplorer.FileUtils;
import com.example.filexplorer.dto.FileEntryDto;
import com.example.filexplorer.dto.FileEntryTagDto;
import com.example.filexplorer.model.FileRegisterModel;
import com.example.filexplorer.model.FileTagsModel;
import com.example.filexplorer.model.FileTagsPK;
import com.example.filexplorer.repository.FileRegisterRepository;
import com.example.filexplorer.repository.FilesTagsRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileRegisterService {
    private final FilesTagsRepository filesTagsRepository;
    private final FileRegisterRepository fileRegisterRepository;
    private final SimpleDateFormat spdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FileRegisterService(FilesTagsRepository filesTagsRepository, FileRegisterRepository fileRegisterRepository) {
        this.filesTagsRepository = filesTagsRepository;
        this.fileRegisterRepository = fileRegisterRepository;
    }

    public List<FileEntryDto> listEntrys(){
        return listEntrys(null);
    }

    public List<FileEntryDto> listEntrys(String fileuuid){
        List<FileEntryDto> entryDtoList = new ArrayList<>();
        List<FileRegisterModel> registers = null;
        if(fileuuid!=null){
            registers = new ArrayList<>(1);
            registers.add(fileRegisterRepository.findById(fileuuid).orElse(null));
        }
        if(registers == null){
            registers = fileRegisterRepository.findAll();
        }

        for(FileRegisterModel register: registers){
            if(register == null){
                continue;
            }
            List<FileTagsModel> tagsModels = filesTagsRepository.findByFileuuid(register.getFileuuid());
            if(tagsModels.isEmpty()){
                continue;
            }
            FileEntryDto entryDto = new FileEntryDto();
            entryDto.setPathencoded(register.getPathEncoded());
            entryDto.setFileuuid(register.getFileuuid());
            for(FileTagsModel tagModel: tagsModels) {
                if(tagModel==null) continue;
                entryDto.addTag(new FileEntryTagDto(tagModel));
            }
            entryDtoList.add(entryDto);
        }
        return entryDtoList;
    }

    public void saveTag(String fileuuid, String key, Object value, String type){
        Optional<FileTagsModel> ftm = filesTagsRepository.findById(new FileTagsPK(fileuuid,key));
        if(ftm.isPresent()){
            return;
        }
        FileTagsModel fileTagsModel = new FileTagsModel(fileuuid, key, ""+value, type);
        filesTagsRepository.save(fileTagsModel);
    }

    public static String fileToEncodedPath(File file){
        return Base64.getEncoder().encodeToString(file.getAbsolutePath().getBytes(StandardCharsets.UTF_8));
    }

    public void readAllDir(String directoryEncoded){
        String path = new String(Base64.getDecoder().decode(directoryEncoded.getBytes()));
        File dir = new File(path);
        if(!dir.exists() || !dir.isDirectory()){
            return;
        }
        for(File file:FileUtils.getFilesRecursive(dir)){
            registerFile(file);
        }
    }

    public void registerFile(File file){
        FileRegisterModel fileRegisterModel = new FileRegisterModel();
        String uuid = UUID.randomUUID().toString();
        fileRegisterModel.setFileuuid(uuid);
        final String pathencoded = Base64.getEncoder().encodeToString(file.getAbsolutePath().getBytes(StandardCharsets.UTF_8));
        fileRegisterModel.setPathEncoded(pathencoded);
        this.fileRegisterRepository.save(fileRegisterModel);

        saveTag(uuid, "date-added", spdf.format(new Date()), "date");
        saveTag(uuid, "filename", file.getName() , "text");
        saveTag(uuid, "path-encoded", pathencoded , "text");
        saveTag(uuid, "path", file.getPath() , "text");
        saveTag(uuid, "size", file.length() , "long");
        saveTag(uuid, "full-path", file.getAbsolutePath() , "text");
        saveTag(uuid, "path", file.getAbsolutePath() , "text");
        saveTag(uuid, "md5-sum", getMD5SumJava(file), "text");


    }

    private static String getMD5SumJava(File file){
        try (InputStream is = Files.newInputStream(Paths.get(file.getAbsolutePath()))){
            return org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    public HistoryModel saveMax(HistoryModel historyModel, int max) {
        HistoryModel saved = this.historyRepository.save(historyModel);
        if (saved.getId() == null || saved.getId() <= max) {
            return saved;
        }
        final Long savedId = saved.getId();
        Long idToBeDeleted = (savedId - max);
        Optional<HistoryModel> selected = this.historyRepository.findById(idToBeDeleted);
        if(!selected.isPresent()){
            return saved;
        }

        this.historyRepository.delete(selected.get());
        return saved;
    }*/
}
