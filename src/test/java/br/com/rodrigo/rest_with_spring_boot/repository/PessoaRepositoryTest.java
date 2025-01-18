package br.com.rodrigo.rest_with_spring_boot.repository;

import br.com.rodrigo.rest_with_spring_boot.model.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PessoaRepositoryTest {
    @Autowired
    private PessoaRepository pessoaRepository;
    Pessoa pessoa;

    @BeforeEach
    void beforeEachMethod() {
        //arrange / given
        pessoa = new Pessoa("Inaldinho", "Silva",
                "Rua qualquer", "masculino", "inaldinho@email.com");

    }

    @Test
    @DisplayName("Deveria Retornar Lista de Pessoas")
    void testListaDePessoas() {
        //arrange / given
        Pessoa pessoa2 = new Pessoa("Rafa", "Gurgel",
                "Otura Rua qualquer", "Feminino", "rafa@email.com");

        pessoaRepository.save(pessoa);
        pessoaRepository.save(pessoa2);
        //act / when
        List<Pessoa> pessoaListActual = pessoaRepository.findAll();

        //Assert / then
        assertNotNull(pessoaListActual);
        assertEquals(2, pessoaListActual.size());
        assertEquals(pessoa.getEmail(), pessoaListActual.get(0).getEmail());

    }

    @Test
    @DisplayName("Deveria Criar uma pessoa")
    void testCriarPessoa() {


        //act / when
        Pessoa pessoaActual = pessoaRepository.save(pessoa);
        //Assert / then
        assertNotNull(pessoaActual);
        assertTrue(pessoaActual.getId() > 0);
        assertEquals(pessoa.getEndereco(), pessoaActual.getEndereco());

    }

    @Test
    @DisplayName("Deveria Retornar uma pessoa por id")
    void testRetornaPessoaPorId() {
        //arrange / given
        pessoaRepository.save(pessoa);
        //act / when
        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findById(pessoa.getId());
        Pessoa pessoaEntity = pessoaEncontrada.get();

        //Assert / then
        assertNotNull(pessoaEntity);
        assertTrue(pessoaEntity.getId() > 0);
        assertEquals(pessoa.getId(), pessoaEntity.getId());

    }

    @Test
    @DisplayName("Deveria Retornar uma pessoa por email")
    void testRetornaPessoaPorEmail() {
        //arrange / given
        pessoaRepository.save(pessoa);
        //act / when
        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findByEmail(pessoa.getEmail());
        Pessoa pessoaEntity = pessoaEncontrada.get();

        //Assert / then
        assertNotNull(pessoaEntity.getEmail());
        assertEquals(pessoa.getEmail(), pessoaEntity.getEmail());

    }

    @Test
    @DisplayName("Deveria Atualizar uma pessoa por id")
    void testAtualizaPessoa() {
        //arrange / given
        pessoaRepository.save(pessoa);

        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findById(pessoa.getId());
        pessoaEncontrada.get().setPrimeiroNome("Erudio");
        pessoaEncontrada.get().setEmail("erudio@email.com");
        Pessoa pessoaEntity = pessoaEncontrada.get();
        //act / when
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoaEntity);

        //Assert / then
        assertNotNull(pessoaAtualizada);
        assertNotEquals("inaldinho@email.com",pessoaAtualizada.getEmail());
        assertNotEquals("Inaldinho", pessoaAtualizada.getPrimeiroNome());
        assertEquals("erudio@email.com", pessoaAtualizada.getEmail());
        assertEquals("Erudio",pessoaAtualizada.getPrimeiroNome());

    }
    @Test
    @DisplayName("Deveria Deletar uma pessoa por id")
    void testDeletarPessoa() {
        //arrange / given
        pessoaRepository.save(pessoa);

        //act / when
        pessoaRepository.delete(pessoa);
        Optional<Pessoa> pessoaEncontrada = pessoaRepository.findById(pessoa.getId());

        //Assert / then
        assertTrue(pessoaEncontrada.isEmpty());

    }


}