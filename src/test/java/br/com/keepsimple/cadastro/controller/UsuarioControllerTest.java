package br.com.keepsimple.cadastro.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import br.com.keepsimple.cadastro.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UsuarioService usuarioService;
    
    private String login = "marcelohkimura@gmail.com";
    private String password = "123456";
    private String cepBaseCorreto = "04216002";
    private String cpfBase1 = "11111111111";
    private String cpfBase2 = "22222222222";    
    
    private String token;

    @Before
    public void setup() throws Exception {
    	token = this.obterToken(login, password);
    }    
    
    @Test
    @Order(1)
    public void criacaoEndpointComSucesso() throws Exception {
    	this.criarEndereco();
    	String usuarioString = "{\"cpf\": \"" + cpfBase1 + "\",\"nome\": \"Fulano\",\"nascimento\": \"20/12/2023\",\"cep\": \"" + cepBaseCorreto + "\",\"numero\": 879, \"complemento\": \"apto 62\"}";
    	
        mockMvc.perform(post("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(usuarioString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        assert(usuarioService.loadUsuarioByCpf(cpfBase1).getCpf().equals(cpfBase1));        
    }
    
    @Test
    @Order(2)
    public void criacaoEndpointCpfJaExiste() throws Exception {
    	String usuarioString = "{\"cpf\": \"" + cpfBase1 + "\",\"nome\": \"Fulano\",\"nascimento\": \"20/12/2023\",\"cep\": \"" + cepBaseCorreto + "\",\"numero\": 879, \"complemento\": \"apto 62\"}";
    	
    	ResultActions result 
        = mockMvc.perform(post("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(usuarioString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    	
    	assert(result.andReturn().getResponse().getContentAsString().equals("Usuário já cadastrado"));    	
    
    }

    @Test
    @Order(3)
    public void criacaoEndpointCpfFaltaDados() throws Exception {
    	String usuarioString = "{\"cpf\": \"" + cpfBase2 + "\",\"nascimento\": \"20/12/2023\",\"cep\": \"" + cepBaseCorreto + "\",\"numero\": 879, \"complemento\": \"apto 62\"}";
    	
    	ResultActions result 
        = mockMvc.perform(post("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(usuarioString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    	
    	assert(result.andReturn().getResponse().getContentAsString().equals("Campo obrigatório não preenchido"));    	
    }     
    
    @Test
    @Order(4)
    public void consultaEndpointCpfCorreto() throws Exception {
    	String cepString = "{\"cpf\":\"" + cpfBase1 + "\"}";
    	
    	ResultActions result 
        = mockMvc.perform(get("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(cepString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        assert(jsonParser.parseMap(resultString).get("cpf").toString().equals(cpfBase1));        
    }
    
    @Test
    @Order(5)
    public void consultaEndpointCpfIncorreto() throws Exception {
    	String cepString = "{\"cpf\":\"" + cpfBase2 + "\"}";
    	
    	ResultActions result 
        = mockMvc.perform(get("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(cepString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    	
    	assert(result.andReturn().getResponse().getContentAsString().equals("Usuário não encontrado"));    	
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
    
    private void criarEndereco() throws Exception {
    	String cepString = "{\"cep\": \"" + cepBaseCorreto + "\",\"bairro\": \"Ipiranga\",\"rua\": \"Mil Oitocentos e Vinte e Dois\",\"cidade\": \"Sao Paulo\",\"uf\": \"SP\"}";
    	
        mockMvc.perform(post("/cep")
        		.header("authorization", "Bearer " + token)
        		.content(cepString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }      
}
