package com.userdrive.service;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;

import com.userdrive.model.ModifiedFile;

@Service
public class RecursiveChangeFinderService implements ChangeFinderService {

	/** API to fetch last n number of modified files in the given directory path. This implementation recursively looks into sub-directories, too.<br>
	 * Child-directories or files for which access permission is not granted will be skipped
	 * @param dirPath path of the directory
	 * @param n number of last modified records to be fetched
	 * @return n paths and last modification times of last n modified files in the given directory  
	 * @throws FileNotFoundException if no file/directory found at given dirPath
	 * @throws IllegalArgumentException if dirPath is null/empty or not a directory
	 * @throws SecurityException if access could not be granted for the directory at given dirPath
	 */
	@Override
	public List<ModifiedFile> getLastModifiedFiles(String dirPath, int n)
			throws FileNotFoundException {
		File userDir = getValidDirFromPath(dirPath);
		validateN(n);
		
		PriorityQueue<ModifiedFile> pq = new PriorityQueue<>(new Comparator<ModifiedFile>() {
			@Override
			public int compare(ModifiedFile o1, ModifiedFile o2) {
				return o1.getModifiedTimeMilliSeconds().compareTo(o2.getModifiedTimeMilliSeconds());
			}
		});
		 
		addLastModifiedFilesRecursive(userDir, pq, n);
		
		LinkedList<ModifiedFile> resList = new LinkedList<ModifiedFile>();
		while(!pq.isEmpty()) {
			resList.addFirst(pq.remove());
		}
		return resList;
		
	}
	
	/** finds last n modified files in the given directory and its child directories, and adds them into the given priority queue
	 * @param dir directory to be searched in
	 * @param pq  priority queue which stores objects of {@link ModifiedFile} 
	 * @param n no of last modified files to be added in pq (priority queue) 
	 * @throws SecurityException - If a security manager exists and its {@link SecurityManager#checkRead(String)} method denies read access to the given directory.
	 * However, during traversing through child directories, it will just skip those directories/files for which access were not granted.  
	 */
	private void addLastModifiedFilesRecursive(File dir, PriorityQueue<ModifiedFile> pq, int n) {
		File[] filesList = dir.listFiles();
		if(filesList == null) {
			System.out.println("Skipping the directory at "+dir.getAbsolutePath() + ", because it could not be accessed due to some error or authentication issue.");
			return;
		}
		for(File file : filesList) {
			if(!file.isDirectory()) {
				try {
					addFile(file, pq, n);
				} catch (SecurityException ex) { // To skip the particular file for access related issue and to let the process continue for other files
					System.out.println("Skipping the file at "+file.getAbsolutePath() + " due to following exception: "); 
					ex.printStackTrace();
				}
			}  else {
				try {
					addLastModifiedFilesRecursive(file, pq, n);
				} catch (SecurityException ex) { // Unauthorized directories/files will be skipped
					System.out.println("Skipping the directory at "+file.getAbsolutePath() + " due to following exception: ");
					ex.printStackTrace(); 
				}
			}
		}	
	}

	/** Creates {@link ModifiedFile} object using given object of {@link File} 
	 * <br>and adds it into the given priority queue ({@link PriorityQueue}) based on following rules:<br>
	 * <br>1. If the given Priority Queue is NOT full, it will simply add the newly created {@link ModifiedFile} object to the queue,<br>
	 * 2. If the given Priority Queue is full, it will add the new {@link ModifiedFile} object to the queue only if 
	 * its last modified time {@link ModifiedFile #getModifiedTimeMilliSeconds()} is greater than the element with
	 * oldest last modified time in the queue
	 * @param file {@link File} object - its information will be used to create {@link ModifiedFile} object  
	 * @param pq Priority Queue (Min-Heap) to store objects of {@link ModifiedFile}   
	 * @param n maximum no of records that can be filled into the given priority queue
	 * @throws SecurityException if read access was not granted for the file or a required system property value could not be accessed 
	 */
	private void addFile(File file, PriorityQueue<ModifiedFile> pq, int n) {
		ModifiedFile modifiedFileObj = new ModifiedFile(file.getAbsolutePath(), file.lastModified());
		if(pq.size() == n) {
			if(pq.peek().getModifiedTimeMilliSeconds().compareTo(file.lastModified()) < 0 ) {
				pq.remove();
				pq.add(modifiedFileObj);
			}
		} else {
			pq.add(modifiedFileObj);
		}
	}
	
	/** validates n for not zero and not negative
	 * @param n
	 * @throws IllegalArgumentException if n is negative or zero 
	 */
	private void validateN(int n) {
		if(n<=0)
			throw new IllegalArgumentException("Value of n can not be negative or zero");
	}

	/** validates dirPath for null, empty,  and removes double quotes from both ends if found
	 * @param dirPath directory path which needs to be validated 
	 * @return dirPath with removed double quotes from both sides if found
	 * @throws FileNotFoundException if no file/directory found at given dirPath
	 * @throws IllegalArgumentException if dirPath is null or empty String or not a directory
	 */
	private File getValidDirFromPath(String dirPath) throws FileNotFoundException {
		if(dirPath == null || "".equals(dirPath))
			throw new IllegalArgumentException("Please provide path of your directory using dirpath parameter. For example, getModifiedFiles?dirpath=\"D:/My Documents\"");
		String resDirPath;
		if(dirPath.startsWith("\"") && dirPath.endsWith("\"")) 
			resDirPath = dirPath.substring(1, dirPath.length()-1);
		else 
			resDirPath = dirPath;
		File dir = new File(resDirPath);
		if(!dir.exists()) {
			throw new FileNotFoundException("Sorry! No Directory was found at given path: "+ dirPath); 
		}
		if(!dir.isDirectory())
			throw new IllegalArgumentException("Given path is not a directory: "+dirPath);
		return dir;
	}
}
