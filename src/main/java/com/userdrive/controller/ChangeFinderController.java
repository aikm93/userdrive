package com.userdrive.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userdrive.model.File;
import com.userdrive.service.ChangeFinderService;

@RestController
public class ChangeFinderController {
	
	private final ChangeFinderService changeFinder;
	
	ChangeFinderController(ChangeFinderService changeFinder) {
		this.changeFinder = changeFinder;
	}
	
	@GetMapping("/getModifiedFiles")
	List<File> getNModifiedFiles(@RequestParam(value = "dirpath") String dirPath,
			@RequestParam(value = "n", defaultValue = "10") int n) throws IOException {
		
		return changeFinder.getLastModifiedFiles(dirPath, n);
	}

	
}
