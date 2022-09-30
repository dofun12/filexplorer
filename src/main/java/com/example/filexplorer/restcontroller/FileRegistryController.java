package com.example.filexplorer.restcontroller;

import com.example.filexplorer.GeneralConstants;
import com.example.filexplorer.dto.FileEntryDto;
import com.example.filexplorer.dto.FileEntryTagDto;
import com.example.filexplorer.service.FileRegisterService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(GeneralConstants.API_PREFIX_PATH+"/fileregistry")
public class FileRegistryController {

    final private FileRegisterService fileRegisterService;

    public FileRegistryController(FileRegisterService fileRegisterService) {
        this.fileRegisterService = fileRegisterService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileEntryDto> getList(){
        return fileRegisterService.listEntrys();
    }


    @GetMapping(value = "/{fileuuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FileEntryDto getFileEntry(@PathVariable("fileuuid") String fileuuid){
        return fileRegisterService.listEntrys(fileuuid, false).get(0);
    }
}
