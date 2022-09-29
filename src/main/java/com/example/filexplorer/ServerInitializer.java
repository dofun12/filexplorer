package com.example.filexplorer;

import com.example.filexplorer.service.FileRegisterService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ServerInitializer implements ApplicationRunner {
    private FileRegisterService fileRegisterService;

    public ServerInitializer(FileRegisterService fileRegisterService) {
        this.fileRegisterService = fileRegisterService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        try {
            fileRegisterService.readAllDir(FileRegisterService.fileToEncodedPath(new File(".")));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}