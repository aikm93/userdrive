package com.userdrive.controller;


import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userdrive.model.ModifiedFile;
import com.userdrive.service.ChangeFinderService;

/**
 * @author Archana Mayani
 * Handles requests to fetch information related to file access and modifications for given drive on server
 */
@RestController
public class ChangeFinderController {
	
	private final ChangeFinderService changeFinder;
	
	ChangeFinderController(ChangeFinderService changeFinder) {
		this.changeFinder = changeFinder;
	}
	
	/** API to fetch last n number of modified files in the given directory path
	 * @param dirPath path of the directory
	 * @param n number of last modified records to be fetched
	 * @return n paths and last modification times of last n modified files in the given directory
	 * @throws FileNotFoundException if no file/directory found at given dirPath
	 * @throws IllegalArgumentException if dirPath is null/empty or not a directory or value of n is negative or zero
	 * @throws SecurityException for any authentication issue while trying to access information at given directory path (dirPath)
	 */
	@GetMapping("/getModifiedFiles")
	List<ModifiedFile> getNModifiedFiles(@RequestParam(value = "dirpath") String dirPath,
			@RequestParam(value = "n", defaultValue = "10") int n) throws FileNotFoundException {
		return changeFinder.getLastModifiedFiles(dirPath, n);
	}

	
}
