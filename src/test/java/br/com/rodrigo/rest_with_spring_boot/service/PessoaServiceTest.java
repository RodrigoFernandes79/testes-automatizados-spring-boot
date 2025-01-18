package br.com.rodrigo.rest_with_spring_boot.service;

import br.com.rodrigo.rest_with_spring_boot.exception.EmailJaEncontradoException;
import br.com.rodrigo.rest_with_spring_boot.model.Pessoa;
import br.com.rodrigo.rest_with_spring_boot.repository.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {
    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;
    @Mock
    private Pessoa pessoa;
    @Mock
    private Optional<Pessoa> pessoaOptional;
    @Captor
    private ArgumentCaptor<Pessoa> pessoaArgumentCaptor;


    @Test
    @DisplayName("Deveria Retornar uma exception quando o email ja existir")
    void criarPessoa_cenario01() {
        //Arrange / given
        given(pessoaRepository.findByEmail(pessoa.getEmail())).willReturn(pessoaOptional);
        given(pessoaOptional.isPresent()).willReturn(true);
        //Act / When  Assertion / Then
        Assertions.assertThrows(EmailJaEncontradoException.class, () -> pessoaService.criarPessoa(pessoa));

    }

    @Test
    @DisplayName("Deveria salvar uma pessoa quando o email não existir")
    void criarPessoa_cenario02() {
        //Arrange / given
        given(pessoaRepository.findByEmail(pessoa.getEmail())).willReturn(pessoaOptional);
        given(pessoaOptional.isPresent()).willReturn(false);

        //Act / When
        pessoaService.criarPessoa(pessoa);
        //Assertion / Then
        //ArgumentCaptor é como dizer:
        //"Quero ver EXATAMENTE o que foi perguntado para mim, para eu conferir se está certo."
        then(pessoaRepository).should().save(pessoaArgumentCaptor.capture());
        assertEquals(pessoa.getEmail(), pessoaArgumentCaptor.getValue().getEmail());
        assertEquals(pessoa.getEndereco(), pessoaArgumentCaptor.getValue().getEndereco());
        assertEquals(pessoa.getPrimeiroNome(), pessoaArgumentCaptor.getValue().getPrimeiroNome());

    }

    @Test
    @DisplayName("Deveria Retornar lista de pessoas quando o metodo listarPessoas() for chamado")
    void listarPessoas_cenario01() {
        //Arrange / Given
        List<Pessoa> pessoaList = new ArrayList<>();
        pessoaList.add(pessoa);
        given(pessoaRepository.findAll()).willReturn(pessoaList);
        //Act / When
       List<Pessoa> pessoasExpected = pessoaService.listarPessoas();
       //Assert / Then
        assertNotNull(pessoasExpected);
        assertEquals(1, pessoasExpected.size());
        assertEquals(pessoa.getPrimeiroNome(),pessoasExpected.get(0).getPrimeiroNome());
        assertEquals(pessoa.getUltimoNome(),pessoasExpected.get(0).getUltimoNome());
        assertEquals(pessoa.getEmail(),pessoasExpected.get(0).getEmail());
    }
}