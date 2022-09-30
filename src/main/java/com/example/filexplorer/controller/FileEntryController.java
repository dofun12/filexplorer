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

	@GetMapping("/fileregistry/{fileuuid}")
	public String greeting(@PathVariable(name = "fileuuid") String fileuuid, Model model) {
		final FileEntryDto entryDto = fileRegisterService.listEntrys(fileuuid).get(0);
		if(entryDto!=null){
			List<BreadDto> breadlist = new ArrayList<>();
			breadlist.add(new BreadDto("List", "/fileregistry/list"));
			breadlist.add(new BreadDto(entryDto.getFileuuid(), "/fileregistry/"+entryDto.getFileuuid(), true));
			model.addAttribute("breadlist", breadlist);
			model.addAttribute("fileentry", entryDto);
		}
		return "fileentry/get-template";
	}

	@GetMapping("/fragments/header")
	public String getHeader(Model model) {
		return "fragments/header";
	}

	@GetMapping({"/fileregistry/list", "/fileregistry/"})
	public String list(Model model) {
		List<BreadDto> breadlist = new ArrayList<>();
		breadlist.add(new BreadDto("List", "/fileregistry/list"));
		model.addAttribute("breadlist", breadlist);
		model.addAttribute("title", "List");
		List<FileEntryDto> entrys = fileRegisterService.listEntrys();
		if(entrys==null || entrys.isEmpty()){
			model.addAttribute("fileentrylist", new ArrayList<>());

			return "fileentry/list-template";
		}
		model.addAttribute("fileentrylist", entrys);
		return "fileentry/list-template";
	}


}