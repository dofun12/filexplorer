package com.example.filexplorer.controller;

import com.example.filexplorer.dto.BreadDto;
import com.example.filexplorer.dto.FileEntryDto;
import com.example.filexplorer.service.FileRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FileEntryController {
    final private FileRegisterService fileRegisterService;

    public FileEntryController(FileRegisterService fileRegisterService) {
        this.fileRegisterService = fileRegisterService;
    }

    final static BreadDto BREAD_HOME = new BreadDto("Home", "/fileregistry/");

    @GetMapping("/fileregistry/{fileuuid}")
    public String greeting(@PathVariable(name = "fileuuid") String fileuuid, Model model) {
        final FileEntryDto entryDto = fileRegisterService.listEntrys(fileuuid).get(0);
        if (entryDto != null) {
            setBreacumb(model,
                    BREAD_HOME,
                    new BreadDto("List", "/fileregistry/list"),
                    new BreadDto(entryDto.getFileuuid(), "/fileregistry/" + entryDto.getFileuuid(), true)
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


}