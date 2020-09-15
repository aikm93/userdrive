package com.userdrive.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;

import com.userdrive.model.File;

@Service
public class ChangeFinderService {
	
	
	public List<File> getLastModifiedFiles(String dirPath, int n) throws IOException {
		if(dirPath.startsWith("\"") && dirPath.endsWith("\"")) 
			dirPath = dirPath.substring(1, dirPath.length()-1); 
		final String userdirpath = dirPath;
		PriorityQueue<File> pq = new PriorityQueue<>(new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				return o1.getModifiedTimeObj().compareTo(o2.getModifiedTimeObj());
			}
		});
		
		
		Files.find(Paths.get(dirPath),
		           Integer.MAX_VALUE,
		           (filePath, fileAttr) -> fileAttr.isRegularFile())
		        .forEach(path -> addLastModifiedFiles(path, pq, n, userdirpath))  ;
		
		
		List<File> resList = new ArrayList<>();
		while(!pq.isEmpty()) {
			resList.add(pq.remove());
		}
		return resList;
		
	}
	
	
	private void addLastModifiedFiles(Path path, PriorityQueue<File> pq, int n, String userdirpath){
		BasicFileAttributes attrs = null;
		try {
			attrs = Files.readAttributes(path, BasicFileAttributes.class);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		File file = new File(path.toAbsolutePath().toString().substring(userdirpath.length()), attrs.lastModifiedTime());
		if(pq.size() == n) {
			if(pq.peek().getModifiedTimeObj().compareTo(attrs.lastModifiedTime()) < 0 ) {
				pq.remove();
				pq.add(file);
			}
		} else {
			pq.add(file);
		}
	}
	
	
	
	
}
