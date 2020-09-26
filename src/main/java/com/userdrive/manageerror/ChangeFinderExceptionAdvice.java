package com.userdrive.manageerror;

import java.io.FileNotFoundException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.http.HttpStatus;

/**
 * @author Archana Mayani
 * Global Exception Handling
 */
@ControllerAdvice
public class ChangeFinderExceptionAdvice {
	
	@ResponseBody
	@ExceptionHandler(InternalServerError.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String internalServerErrorHandler(InternalServerError ex) {
		ex.printStackTrace();
		return "Some error occured while processing your request. Please try again.";
	}
	
	@ResponseBody
	@ExceptionHandler(OutOfMemoryError.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String OutOfMemoryErrorHandler(OutOfMemoryError ex) {
		ex.printStackTrace();
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(NoSuchFileException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String noSuchFileExceptionHandler(NoSuchFileException ex) {
		ex.printStackTrace();
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	String accessDeniedExceptionHandler(AccessDeniedException ex) {
		ex.printStackTrace();
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(SecurityException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	String securityExceptionHandler(SecurityException ex) {
		ex.printStackTrace();
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String missingParamHandler(MissingServletRequestParameterException ex) {
		ex.printStackTrace();
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String numberFormatExceptionHandler(NumberFormatException ex) {
		ex.printStackTrace();
		return ex.getMessage()+ " : Input could not be interpreted as a number. It is either out of supported range or not a valid number.";
	}
	
	@ResponseBody
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String illegalArgumentExceptionHandler(IllegalArgumentException ex) {
		ex.printStackTrace();
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String nullPointerExceptionHandler(NullPointerException ex) {
		ex.printStackTrace();
		return "Some error occured while processing your request. Please try again.";
	}
	
	@ResponseBody
	@ExceptionHandler(FileNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String fileNotFoundExceptionHandler(FileNotFoundException ex) {
		ex.printStackTrace();
		return ex.getMessage();
	}

}
