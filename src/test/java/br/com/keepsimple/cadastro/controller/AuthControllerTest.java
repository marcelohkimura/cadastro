package br.com.keepsimple.cadastro.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    private String login = "marcelohkimura@gmail.com";
    private String passwordCorreta = "123456";
    private String passwordIncorreta = "1234567";
    
    @Test
    public void loginComSucesso() throws Exception {
    	String loginString = "{\"email\":\"" + login + "\",\"password\":\"" + passwordCorreta + "\"}";

        ResultActions result 
        = mockMvc.perform(post("/auth/login")
      	.content(loginString)     
      	.contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType("application/json"));

      String resultString = result.andReturn().getResponse().getContentAsString();

      JacksonJsonParser jsonParser = new JacksonJsonParser();
      
      // Valida que retornou o token
      assertNotNull(jsonParser.parseMap(resultString).get("token").toString());
    }
    
    @Test
    public void loginInvalido() throws Exception {
    	String loginString = "{\"email\":\"" + login + "\",\"password\":\"" + passwordIncorreta + "\"}";

        mockMvc.perform(post("/auth/login")
        	.content(loginString)     
        	.contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }    
}
