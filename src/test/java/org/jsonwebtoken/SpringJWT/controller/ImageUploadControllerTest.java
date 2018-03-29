/*package org.jsonwebtoken.SpringJWT.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;


import org.jsonwebtoken.SpringJWT.SpringJwtApplicationTest;
import org.jsonwebtoken.SpringJWT.secured.controller.ImageUploadController;
import org.jsonwebtoken.SpringJWT.service.ImageUploadService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ImageUploadControllerTest extends SpringJwtApplicationTest {
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private ImageUploadService service;
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	@Before
	public void setUp() throws Exception {
	String uploadPath = folder.getRoot().getAbsolutePath();
	mockMvc = MockMvcBuilders
	.standaloneSetup(new File(uploadPath))
	.build();
	}
	
	
	
	
	
	@Test
	public void saveUploadFileToPath() throws Exception{
		String jwtToken = login();
		MockMultipartFile file = new MockMultipartFile("file", "sudhir_3.jpg", MediaType.IMAGE_JPEG_VALUE, "Hello, World!".getBytes());
		MvcResult result = this.mockMvc.perform(post("/secured/image/uploadImage")
								.header("Authorization", jwtToken)).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertEquals("File has been successfully uploaded in directory:!", result.getResponse().getContentAsString());
	}
	
	 @Test
	    public void shouldSaveUploadedFile() throws Exception {
		 String jwtToken = login();
	        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
	                "text/plain", "Spring Framework".getBytes());
	        this.mockMvc.perform(post("/secured/image/uploadImage").file(multipartFile))
	                .andExpect(status().isFound())
	                .andExpect(header().string("Location", "/"));
	        this.mockMvc.perform(post("/secured/image/uploadImage").header("Authorization", jwtToken).file(multipartFile))

	        then(this.service).should().store(multipartFile);
	    }

	private Object fileUpload(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}




*/