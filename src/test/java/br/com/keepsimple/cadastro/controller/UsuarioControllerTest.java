package br.com.keepsimple.cadastro.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import br.com.keepsimple.cadastro.model.Status;
import br.com.keepsimple.cadastro.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UsuarioService usuarioService;
    
    private String login = "marcelohkimura@gmail.com";
    private String password = "123456";
    private String cepBaseCorreto = "04216002";
    private String cepBaseIncorreto = "04216003";
    private String cpfBase1 = "11111111111";
    private String cpfBase2 = "22222222222";    
    
    private String token;

    @Before
    public void setup() throws Exception {
    	token = this.obterToken(login, password);
    }    
    
    @Test
    public void A_criacaoEndpointComSucesso() throws Exception {
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
    public void B_criacaoEndpointCpfJaExiste() throws Exception {
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
    public void C_criacaoEndpointCpfFaltaDados() throws Exception {
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
    public void D_consultaEndpointCpfCorreto() throws Exception {
    	String usuarioString = "{\"cpf\":\"" + cpfBase1 + "\"}";
    	
    	ResultActions result 
        = mockMvc.perform(get("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(usuarioString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        assert(jsonParser.parseMap(resultString).get("cpf").toString().equals(cpfBase1));        
    }
    
    @Test
    public void E_consultaEndpointCpfIncorreto() throws Exception {
    	String usuarioString = "{\"cpf\":\"" + cpfBase2 + "\"}";
    	
    	ResultActions result 
        = mockMvc.perform(get("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(usuarioString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    	
    	assert(result.andReturn().getResponse().getContentAsString().equals("Usuário não encontrado"));    	
    }    
    
    @Test
    public void F_alteracaoEndpointComSucesso() throws Exception {
    	String usuarioString = "{\"cpf\": \"" + cpfBase1 + "\",\"nome\": \"Fulano\",\"nascimento\": \"20/12/2023\",\"cep\": \"" + cepBaseCorreto + "\",\"numero\": 879, \"complemento\": \"apto 62\"}";
    	
        mockMvc.perform(put("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(usuarioString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        assert(usuarioService.loadUsuarioByCpf(cpfBase1).getCpf().equals(cpfBase1));        
    }
    
    @Test
    public void G_alteracaoEndpointCepNaoExiste() throws Exception {
    	String usuarioString = "{\"cpf\": \"" + cpfBase2 + "\",\"nome\": \"Fulano\",\"nascimento\": \"20/12/2023\",\"cep\": \"" + cepBaseIncorreto + "\",\"numero\": 879, \"complemento\": \"apto 62\"}";
    	
    	ResultActions result 
        = mockMvc.perform(put("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(usuarioString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        assert(result.andReturn().getResponse().getContentAsString().equals("CEP não encontrado"));        
    }    
    
    @Test
    public void H_alteracaoEndpointCpfNaoExiste() throws Exception {
    	String usuarioString = "{\"cpf\": \"" + cpfBase2 + "\",\"nome\": \"Fulano\",\"nascimento\": \"20/12/2023\",\"cep\": \"" + cepBaseCorreto + "\",\"numero\": 879, \"complemento\": \"apto 62\"}";
    	
    	ResultActions result 
        = mockMvc.perform(put("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(usuarioString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        assert(result.andReturn().getResponse().getContentAsString().equals("Usuário não cadastrado"));        
    }
    
    @Test
    public void I_removerEndpointComSucesso() throws Exception {
    	String usuarioString = "{\"cpf\":\"" + cpfBase1 + "\"}";
    	
        mockMvc.perform(delete("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(usuarioString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        assert(usuarioService.loadUsuarioByCpf(cpfBase1).getStatus().equals(Status.R));        
    }
    
    @Test
    public void J_removerEndpointCpfNaoExiste() throws Exception {
    	String usuarioString = "{\"cpf\":\"" + cpfBase2 + "\"}";
    	
    	ResultActions result 
        = mockMvc.perform(delete("/usuario")
        		.header("authorization", "Bearer " + token)
        		.content(usuarioString) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        assert(result.andReturn().getResponse().getContentAsString().equals("Usuário não cadastrado"));        
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
