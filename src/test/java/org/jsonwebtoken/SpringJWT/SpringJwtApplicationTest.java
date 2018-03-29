package org.jsonwebtoken.SpringJWT;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jsonwebtoken.SpringJWT.security.JwtAuthenticationRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringJwtApplication.class)
@WebAppConfiguration
public class SpringJwtApplicationTest {

	/*@Test
	public void contextLoads() {
		
	}*/
	
	@Autowired
	public MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@SuppressWarnings("rawtypes")
	public HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
		@Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

	@Before
    public void setup() throws Exception {
    	this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
	
	public String  getTokenFromResponse(String jsonResponse){
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object>  map = new HashMap<>();
		try {
			map = mapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>(){});
			
			return (String) map.get("token");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public String login() throws Exception{
		JwtAuthenticationRequest loginDto = new JwtAuthenticationRequest();
		loginDto.setUsername("Nagendra");
		loginDto.setPassword("password");
		String login = json(loginDto);
		
		MvcResult result = this.mockMvc.perform(post("/api/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(login))
				.andExpect(status().isOk()).andReturn();
		String jsonResponse = result.getResponse().getContentAsString();
		String token = getTokenFromResponse(jsonResponse);
		System.out.println(token);
		return "Bearer " + token;
	}
}
