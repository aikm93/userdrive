package com.userdrive.manageerror;

import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
@ControllerAdvice
public class ChangeFinderExceptionAdvice {
	@ResponseBody
	  @ExceptionHandler(NoSuchFileException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String dirNotFoundHandler(NoSuchFileException ex) {
	    ex.printStackTrace();
		return "Directory not found! "
				+ "Please make sure value of dirpath is passed in valid spelling and format: "
				+ "for example, dirpath=\"d:/my-documents/bills\"";
	  }
	
	@ResponseBody
	  @ExceptionHandler(AccessDeniedException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String accessDeniedHandler(AccessDeniedException ex) {
	    ex.printStackTrace();
		return "Access denied for this directory.";
	  }
	
	@ResponseBody
	  @ExceptionHandler(MissingServletRequestParameterException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String missingParamHandler(MissingServletRequestParameterException ex) {
	    ex.printStackTrace();
		return "Missing parameter: "+ex.getParameterName()+ "";
	  }
	@ResponseBody
	  @ExceptionHandler(InvalidPathException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String invalidPathExceptionHandler(InvalidPathException ex) {
	    ex.printStackTrace();
		return "Invalid Path, if a path contains space, please enclose it in double quotes. Example, dirpath=\"D:/Project/CRM Workspace\" ";
	  }
	
	
	
}
