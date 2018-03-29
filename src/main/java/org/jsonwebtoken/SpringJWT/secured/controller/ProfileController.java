package org.jsonwebtoken.SpringJWT.secured.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsonwebtoken.SpringJWT.model.Images;
import org.jsonwebtoken.SpringJWT.model.User;
import org.jsonwebtoken.SpringJWT.service.ImageUploadService;
import org.jsonwebtoken.SpringJWT.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ProfileController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ImageUploadService imageService;
		
	List<String> files = new ArrayList<String>();
			@GetMapping("/getImage")
			public ResponseEntity<List<String>> getImage(HttpServletRequest request,HttpServletResponse response)throws IOException{
				final int BUFFER_SIZE = 4096;
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				User user = this.userService.findByUsername(username);
				Images image = user.getImages();
				ServletContext context = request.getServletContext();
				String filepath = imageService.getSaveFilePathByUser(image.getId());
				System.out.println("getting the filepath from current user:"+filepath);
				String fullPath = filepath;
				File downloadFile = new File(fullPath);
				String filename = downloadFile.getName();
				files.add(filename);
				System.out.println("List contains:"+files);
				
				List<String> fileNames = files
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(ProfileController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());
				files.clear();
				System.out.println("after clearing the list:"+files);
				System.out.println("passed file is:"+fileNames);
				return ResponseEntity.ok().body(fileNames);
				
				
			}
			
			@GetMapping("/files/{filename:.+}")
			@ResponseBody
			public ResponseEntity<Resource> getFile(@PathVariable String filename) {
				Resource file = imageService.loadFile(filename);
				System.out.println("file name is:"+file);
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
						.body(file);
			}



}
