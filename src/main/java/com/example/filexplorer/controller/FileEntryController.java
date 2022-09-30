package com.example.filexplorer.controller;

import com.example.filexplorer.dto.BreadDto;
import com.example.filexplorer.dto.DirectoryDto;
import com.example.filexplorer.dto.FileEntryDto;
import com.example.filexplorer.service.FileRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class FileEntryController {
    final private FileRegisterService fileRegisterService;

    public FileEntryController(FileRegisterService fileRegisterService) {
        this.fileRegisterService = fileRegisterService;
    }

    final static BreadDto BREAD_HOME = new BreadDto("Home", "/fileregistry/");

    @GetMapping("/fileregistry/details/{fileuuid}")
    public String greeting(@PathVariable(name = "fileuuid") String fileuuid, Model model) {
        final FileEntryDto entryDto = fileRegisterService.listEntrys(fileuuid, false).get(0);
        if (entryDto != null) {
            setBreacumb(model,
                    BREAD_HOME,
                    new BreadDto("List", "/fileregistry/list"),
                    new BreadDto("Details /"+entryDto.getName(), "/fileregistry/details/" + entryDto.getFileuuid(), true)
            );
            model.addAttribute("fileentry", entryDto);
        }
        return "fileentry/get-template";
    }

    private void setBreacumb(Model model, BreadDto... breads) {
        List<BreadDto> breadlist = new ArrayList<>();
        for (BreadDto bread : breads) {
            breadlist.add(bread);
        }
        model.addAttribute("breadlist", breadlist);
    }

    @GetMapping("/fragments/header")
    public String getHome(Model model) {
        return "fragments/header";
    }

    @GetMapping("/fileregistry/")
    public String getHeader(Model model) {
        setBreacumb(model,
                new BreadDto(BREAD_HOME.getName(), BREAD_HOME.getLink(), true)
        );
        return "fileentry/home-template";
    }

    @PostMapping("/fileregistry/add")
    public String directoryAddSubmit(@ModelAttribute DirectoryDto directoryDto, Model model) {
        setBreacumb(model,
            BREAD_HOME,
            new BreadDto("Add", "/fileregistry/add", true)
        );
        if(directoryDto.getPath()==null){
            model.addAttribute("postMessage", "Diretorio invalido");
            return "fileentry/post-template";
        }
        File dir = new File(directoryDto.getPath());
        if(!dir.exists() || !dir.isDirectory()){
            model.addAttribute("postMessage", "Diretorio não encontrado");
            return "fileentry/post-template";
        }

        fileRegisterService.readAllDir(FileRegisterService.fileToEncodedPath(dir));
        model.addAttribute("postMessage", "Gravado com sucesso");
        model.addAttribute("directoryDto", directoryDto);
        return "fileentry/post-template";
    }

    @GetMapping("/fileregistry/add")
    public String directoryAdd(Model model) {
        setBreacumb(model,
                BREAD_HOME,
                new BreadDto("Add", "/fileregistry/add")
        );
        model.addAttribute("directoryDto", new DirectoryDto());
        return "fileentry/add-template";
    }


    @GetMapping({"/fileregistry/list"})
    public String list(Model model) {
        setBreacumb(model,
                BREAD_HOME,
                new BreadDto("List", "/fileregistry/list")
        );
        model.addAttribute("title", "List");
        List<FileEntryDto> entrys = fileRegisterService.listEntrys();
        if (entrys == null || entrys.isEmpty()) {
            model.addAttribute("fileentrylist", new ArrayList<>());

            return "fileentry/list-template";
        }
        model.addAttribute("fileentrylist", entrys);
        return "fileentry/list-template";
    }

    @GetMapping({"/fileregistry/list/{fileuuid}"})
    public String listRootDir(@PathVariable(name = "fileuuid") String fileuuid,Model model) {
        if(fileuuid==null){
            return "notfound";
        }
        FileEntryDto entryDto = fileRegisterService.getFileEntry(fileuuid, Optional.empty());
        if(entryDto==null){
            return "notfound";
        }
        setBreacumb(model,
                BREAD_HOME,
                new BreadDto("List", "/fileregistry/list"),
                new BreadDto(entryDto.getName(), "/fileregistry/list/"+fileuuid)
        );
        model.addAttribute("title", "List");
        List<FileEntryDto> entrys = fileRegisterService.listEntrysFromParent(fileuuid);
        if (entrys == null || entrys.isEmpty()) {
            model.addAttribute("fileentrylist", new ArrayList<>());

            return "fileentry/list-template";
        }
        model.addAttribute("fileentrylist", entrys);
        return "fileentry/list-template";
    }

}