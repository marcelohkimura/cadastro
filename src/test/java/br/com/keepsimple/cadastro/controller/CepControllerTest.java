package br.com.keepsimple.cadastro.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import br.com.keepsimple.cadastro.service.CepService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CepControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private CepService cepService;
    
    private String cepBaseOk = "04216001";
    private String cepBaseNok = "04216003";
    private String login = "marcelohkimura@gmail.com";
    private String password = "123456";
    
    private String token;

    @Before
    public void setup() throws Exception {
    	token = this.obterToken(login, password);
    }
    
    @Test
    public void A_criacaoEndpointComSucesso() throws Exception {
    	String cepString = "{\"cep\": \"" + cepBaseOk + "\",\"bairro\": \"Ipiranga\",\"rua\": \"Mil Oitocentos e Vinte e Dois\",\"cidade\": \"Sao Paulo\",\"uf\": \"SP\"}";
    	
        mockMvc.perform(post("/cep")
        		.header("authorization", "Bearer " + token)
        		.content(cepString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        assert(cepService.loadCepByCodigo(cepBaseOk).getCep().equals(cepBaseOk));        
    }    
    
    @Test
    public void B_criacaoEndpointCepJaExiste() throws Exception {
    	String cepString = "{\"cep\": \"" + cepBaseOk + "\",\"bairro\": \"Ipiranga\",\"rua\": \"Mil Oitocentos e Vinte e Dois\",\"cidade\": \"Sao Paulo\",\"uf\": \"SP\"}";
    	
    	ResultActions result 
        = mockMvc.perform(post("/cep")
        		.header("authorization", "Bearer " + token)
        		.content(cepString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    	
    	assert(result.andReturn().getResponse().getContentAsString().equals("CEP já existente"));    	
    }
    
    @Test
    public void C_criacaoEndpointCepFaltaDados() throws Exception {
    	String cepString = "{\"cep\": \"" + cepBaseNok + "\",\"rua\": \"Mil Oitocentos e Vinte e Dois\",\"cidade\": \"Sao Paulo\",\"uf\": \"SP\"}";
    	
    	ResultActions result 
        = mockMvc.perform(post("/cep")
        		.header("authorization", "Bearer " + token)
        		.content(cepString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    	
    	assert(result.andReturn().getResponse().getContentAsString().equals("Campo obrigatório não preenchido"));    	
    }     
    
    @Test
    public void D_consultaEndpointCepCorreto() throws Exception {
    	String cepString = "{\"cep\":\"" + cepBaseOk + "\"}";
    	
    	ResultActions result 
        = mockMvc.perform(get("/cep")
        		.header("authorization", "Bearer " + token)
        		.content(cepString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        assert(jsonParser.parseMap(resultString).get("cep").toString().equals(cepBaseOk));        
    }
    
    @Test
    public void E_consultaEndpointCepIncorreto() throws Exception {
    	String cepString = "{\"cep\":\"" + cepBaseNok + "\"}";
    	
    	ResultActions result 
        = mockMvc.perform(get("/cep")
        		.header("authorization", "Bearer " + token)
        		.content(cepString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    	
    	assert(result.andReturn().getResponse().getContentAsString().equals("CEP não encontrado"));    	
    }    
    
    private String obterToken(String login, String password) throws Exception {
    	 
    	String loginString = "{\"email\":\"" + login + "\",\"password\":\"" + password + "\"}";

        ResultActions result 
          = mockMvc.perform(post("/auth/login")
        	.content(loginString)     
        	.contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }    
}
