package com.example.filexplorer.service;

import com.example.filexplorer.FileUtils;
import com.example.filexplorer.dto.FileEntryDto;
import com.example.filexplorer.dto.FileEntryTagDto;
import com.example.filexplorer.model.DirectoryModel;
import com.example.filexplorer.model.FileRegisterModel;
import com.example.filexplorer.model.FileTagsModel;
import com.example.filexplorer.model.FileTagsPK;
import com.example.filexplorer.repository.DirectoryRegisterRepository;
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
    private final DirectoryRegisterRepository directoryRegisterRepository;
    private final FileRegisterRepository fileRegisterRepository;
    private final SimpleDateFormat spdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public FileRegisterService(FilesTagsRepository filesTagsRepository, DirectoryRegisterRepository directoryRegisterRepository, FileRegisterRepository fileRegisterRepository) {
        this.filesTagsRepository = filesTagsRepository;
        this.directoryRegisterRepository = directoryRegisterRepository;
        this.fileRegisterRepository = fileRegisterRepository;
    }

    public List<FileEntryDto> listEntrys() {
        return listEntrys(null, true);
    }

    public List<FileEntryDto> listEntrysFromParent(String fileuuid){
        return getFileEntryDtos(fileRegisterRepository.findAllByParentfileuuid(fileuuid));
    }

    public FileEntryDto getFileEntry(String fileuuid, Optional<FileRegisterModel> optRegisterModel) {
        FileRegisterModel registerModel = null;
        if(optRegisterModel.isPresent()){
            registerModel = optRegisterModel.get();
        }
        if(fileuuid != null){
            registerModel = fileRegisterRepository.findById(fileuuid).orElse(null);
        }
        if (registerModel == null) {
            return null;
        }
        FileEntryDto entryDto = new FileEntryDto();
        entryDto.setName(registerModel.getName());
        entryDto.setParentFileuuid(registerModel.getParentfileuuid());
        entryDto.setPathencoded(registerModel.getPathEncoded());
        entryDto.setFileuuid(registerModel.getFileuuid());
        List<FileTagsModel> tagsModels = filesTagsRepository.findByFileuuid(registerModel.getFileuuid());
        if (tagsModels.isEmpty()) {
            return entryDto;
        }
        for (FileTagsModel tagModel : tagsModels) {
            if (tagModel == null) continue;
            entryDto.addTag(new FileEntryTagDto(tagModel));
        }
        return entryDto;
    }

    private List<FileRegisterModel> searchEntrys(String fileuuid, boolean onlyroot) {
        List<FileRegisterModel> registers = new ArrayList<>();
        if (fileuuid == null && onlyroot) {
            return fileRegisterRepository.findAllByParentfileuuidIsNull();
        }
        if (fileuuid != null && onlyroot) {
            return fileRegisterRepository.findAllByFileuuidAndParentfileuuidIsNull(fileuuid);
        }
        if (fileuuid != null) {
            registers.add(fileRegisterRepository.findById(fileuuid).orElse(new FileRegisterModel()));
            return registers;
        }
        return fileRegisterRepository.findAll();
    }

    public List<FileEntryDto> listEntrys(String fileuuid, boolean onlyroot) {
        List<FileRegisterModel> registers = searchEntrys(fileuuid, onlyroot);
        return getFileEntryDtos(registers);
    }

    private List<FileEntryDto> getFileEntryDtos(List<FileRegisterModel> registers) {
        List<FileEntryDto> entryDtoList = new ArrayList<>();
        for (FileRegisterModel register : registers) {
            if (register == null) {
                continue;
            }
            entryDtoList.add(getFileEntry(null, Optional.of(register)));
        }
        return entryDtoList;
    }

    public void saveTag(String fileuuid, String key, Object value, String type) {
        Optional<FileTagsModel> ftm = filesTagsRepository.findById(new FileTagsPK(fileuuid, key));
        if (ftm.isPresent()) {
            return;
        }
        FileTagsModel fileTagsModel = new FileTagsModel(fileuuid, key, "" + value, type);
        filesTagsRepository.save(fileTagsModel);
    }

    public static String fileToEncodedPath(File file) {
        return Base64.getEncoder().encodeToString(file.getAbsolutePath().getBytes(StandardCharsets.UTF_8));
    }

    public FileRegisterModel readAllDir(String directoryEncoded) {
        return readAllDir(directoryEncoded, false);
    }

    public static File getFileFromEncodedPath(String encodedPath){
        String path = new String(Base64.getDecoder().decode(encodedPath.getBytes()));
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        return dir;
    }

    public FileRegisterModel readAllDir(String directoryEncoded, boolean scan) {
        String path = new String(Base64.getDecoder().decode(directoryEncoded.getBytes()));
        File dir = new File(path);
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }

        FileRegisterModel directoryModel = new FileRegisterModel();
        directoryModel.setName(dir.getName());
        directoryModel.setFileuuid(UUID.randomUUID().toString());
        directoryModel.setParentfileuuid(null);
        directoryModel.setPathEncoded(directoryEncoded);
        final FileRegisterModel saved = fileRegisterRepository.save(directoryModel);
        final String uuid = saved.getFileuuid();

        saveTag(uuid, "date-added", spdf.format(new Date()), "date");
        saveTag(uuid, "isdirectory", dir.isDirectory(), "boolean");
        saveTag(uuid, "filename", dir.getName(), "text");
        saveTag(uuid, "path-encoded", directoryEncoded, "text");
        saveTag(uuid, "path", dir.getPath(), "text");
        saveTag(uuid, "size", dir.length(), "long");
        saveTag(uuid, "full-path", dir.getAbsolutePath(), "text");
        saveTag(uuid, "path", dir.getAbsolutePath(), "text");
        if (!scan) {
            return saved;
        }
        for (File file : FileUtils.getFilesRecursive(dir)) {
            registerFile(directoryModel.getFileuuid(), file);
        }
        return saved;
    }

    public FileEntryDto registerFile(String diruuid, File file) {
        FileRegisterModel fileRegisterModel = new FileRegisterModel();
        String uuid = UUID.randomUUID().toString();
        fileRegisterModel.setFileuuid(uuid);
        final String pathencoded = Base64.getEncoder().encodeToString(file.getAbsolutePath().getBytes(StandardCharsets.UTF_8));
        fileRegisterModel.setPathEncoded(pathencoded);
        fileRegisterModel.setParentfileuuid(diruuid);
        fileRegisterModel.setName(file.getName());
        this.fileRegisterRepository.save(fileRegisterModel);

        saveTag(uuid, "date-added", spdf.format(new Date()), "date");
        saveTag(uuid, "filename", file.getName(), "text");
        saveTag(uuid, "path-encoded", pathencoded, "text");
        saveTag(uuid, "path", file.getPath(), "text");
        saveTag(uuid, "size", file.length(), "long");
        saveTag(uuid, "full-path", file.getAbsolutePath(), "text");
        saveTag(uuid, "path", file.getAbsolutePath(), "text");
        saveTag(uuid, "md5-sum", getMD5SumJava(file), "text");
        return getFileEntry(null, Optional.of(fileRegisterModel));

    }

    private static String getMD5SumJava(File file) {
        try (InputStream is = Files.newInputStream(Paths.get(file.getAbsolutePath()))) {
            return org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
        } catch (Exception e) {
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
