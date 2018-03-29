package org.jsonwebtoken.SpringJWT.secured.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.jsonwebtoken.SpringJWT.dto.ImageDto;
import org.jsonwebtoken.SpringJWT.exception.StorageException;
import org.jsonwebtoken.SpringJWT.exception.StorageFileNotFoundException;
import org.jsonwebtoken.SpringJWT.model.Images;
import org.jsonwebtoken.SpringJWT.model.User;
import org.jsonwebtoken.SpringJWT.service.ImageUploadService;
import org.jsonwebtoken.SpringJWT.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
@RestController
@RequestMapping("/secured/image")
public class ImageUploadController  {
	private final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
	
	
	@Autowired
	ImageUploadService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Environment env;
	
	public ImageUploadController() {
		
	}
	
	public ImageUploadController(ImageUploadService service, UserService userService, Environment env,
			List<String> files) {
		super();
		this.service = service;
		this.userService = userService;
		this.env = env;
		this.files = files;
	}



	List<String> files = new ArrayList<String>();
	
	

	@PostMapping("/uploadImage")
	public ResponseEntity<String> handleFileUpload(@ModelAttribute ImageDto dto) throws StorageException {
		String message = "";
		MultipartFile file = dto.getFile();
		String filename = file.getOriginalFilename();
		if (file.isEmpty()) {
			//message = "Invalid File";
			throw new StorageException("Failed to store empty file " + filename);
		}
		if (filename.contains("*..")) {
            // This is a security check
            throw new StorageException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }
		try {
			//String filename = file.getOriginalFilename();
			String directory = env.getProperty("upload.file.path");
			String uploadFilePath = Paths.get("." + File.separator + directory, filename).toString();
			final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(uploadFilePath)));
            stream.write(file.getBytes());
            service.saveImages(uploadFilePath);
            message = "You successfully uploaded " + filename + "!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			 throw new StorageException(
	                    "Cannot store file with relative path outside current directory "
	                            + file.getOriginalFilename());
			//message = "FAIL to upload " + file.getOriginalFilename() + "!";
			
			//return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}
	
	
	
	@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
	}
