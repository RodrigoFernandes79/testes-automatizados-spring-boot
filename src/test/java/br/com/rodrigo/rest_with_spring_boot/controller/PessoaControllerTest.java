package br.com.rodrigo.rest_with_spring_boot.controller;

import br.com.rodrigo.rest_with_spring_boot.exception.IdNotFoundException;
import br.com.rodrigo.rest_with_spring_boot.model.Pessoa;
import br.com.rodrigo.rest_with_spring_boot.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureJsonTesters
class PessoaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PessoaService pessoaService;
    @Autowired
    private JacksonTester<Pessoa> pessoaJacksonTester;
    private Pessoa pessoa;

    @BeforeEach
    void BeforeEachMethod() {
        //arrange / given
        pessoa = new Pessoa("Inaldinho", "Silva",
                "Rua qualquer", "masculino", "inaldinho@email.com");
    }

    @Test
    @DisplayName("Deveria retornar status 201 created e retorna Pessoa salva")
    void cadastrarPessoa() throws Exception {
        //Arrange / Given
        given(pessoaService.criarPessoa(any(Pessoa.class))).willReturn(pessoa);
        //Act / when
        var response = mockMvc.perform(post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaJacksonTester.write(pessoa).getJson()))

                //Assertions / then
                .andExpect(status().isCreated()) // Verifica o status HTTP 201
                .andExpect(jsonPath("$.primeiroNome").value(pessoa.getPrimeiroNome())) // Verifica o campo "primeiroNome"
                .andExpect(jsonPath("$.email").value(pessoa.getEmail())); // Verifica o campo "email"
        assertNotNull(response);

    }

    @Test
    @DisplayName("Deveria retornar status 200 OK e retorna Lista de Pessoas salvas")
    void listarPessoas() throws Exception {
        //Arrange / Given
        List<Pessoa> pessoaList = new ArrayList<>();
        pessoaList.add(pessoa);
        given(pessoaService.listarPessoas()).willReturn(pessoaList);
        //Act / when
        var response = mockMvc.perform(get("/pessoas"))

                //Assertions / then
                .andExpect(status().isOk()) // Verifica o status HTTP 200
                .andExpect(jsonPath("$.size()").value(pessoaList.size())) // Verifica o tamanho da lista
                .andExpect(jsonPath("$[0].primeiroNome").value(pessoa.getPrimeiroNome())) // Verifica o primeiro nome da pessoa
                .andExpect(jsonPath("$[0].email").value(pessoa.getEmail())); // Verifica o email da pessoa

        assertNotNull(response);

    }

    @Test
    @DisplayName("Deveria retornar status 200 OK e retorna um Objeto Pessoa pelo id")
    void listarPessoaPorId_cenario01() throws Exception {
        //Arrange / Given
        Long id = 1L;
        given(pessoaService.encontrarPessoaPorId(id)).willReturn(pessoa);
        //Act / when
        var response = mockMvc.perform(get("/pessoas/{id}", id))

                //Assertions / then
                .andExpect(status().isOk())// Verifica o status HTTP 200
                .andExpect(jsonPath("$.primeiroNome").value(pessoa.getPrimeiroNome())) // Verifica o primeiro nome da pessoa
                .andExpect(jsonPath("$.email").value(pessoa.getEmail())); // Verifica o email da pessoa

        assertNotNull(response);

    }

    @Test
    @DisplayName("Deveria retornar status 404 Not Found quando o id nao existir")
    void listarPessoaPorId_cenario02() throws Exception {
        //Arrange / Given
        Long id = 1L;
        given(pessoaService.encontrarPessoaPorId(id)).willThrow(IdNotFoundException.class);
        //Act / when
        var response = mockMvc.perform(get("/pessoas/{id}", id))

                //Assertions / then
                .andExpect(status().isNotFound());// Verifica o status HTTP 404

    }

    @Test
    @DisplayName("Deveria retornar status 200 OK e atualiza Pessoa pelo id")
    void atualizarPessoa_cenario01() throws Exception {
        //Arrange / Given
        Long id = 1L;
        given(pessoaService.encontrarPessoaPorId(id)).willReturn(pessoa);
        Pessoa pessoaAtualizada = new Pessoa("Erudio", "Silva",
                "Outra rua qualquer", "masculino", "erudio@email.com");
        given(pessoaService.atualizarPessoa(any(), any(Pessoa.class))).willReturn(pessoaAtualizada);
        //Act / when
        var response = mockMvc.perform(put("/pessoas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pessoaJacksonTester.write(pessoaAtualizada).getJson()))

                //Assertions / then
                .andExpect(status().isOk())// Verifica o status HTTP 200
                .andExpect(jsonPath("$.primeiroNome").value(pessoaAtualizada.getPrimeiroNome())) // Verifica o primeiro nome da pessoa
                .andExpect(jsonPath("$.email").value(pessoaAtualizada.getEmail())); // Verifica o email da pessoa

    }

}