package com.userdrive.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.userdrive.model.ModifiedFile;

public interface ChangeFinderService {
	
	
	/** API to fetch last n number of modified files in the given directory path
	 * @param dirPath path of the directory to be searched for last modified files
	 * @param n number of last modified records to be fetched
	 * @return n paths and last modification times of last n modified files in the given directory  
	 * @throws FileNotFoundException if no file/directory found at given dirPath
	 * @throws IllegalArgumentException if dirPath is null/empty or not a directory or value of n is negative or zero
	 * @throws SecurityException for any authentication issue while trying to access file information at given directory path (dirPath)
	 */
	public List<ModifiedFile> getLastModifiedFiles(String dirPath, int n) throws FileNotFoundException;
	
	
}
