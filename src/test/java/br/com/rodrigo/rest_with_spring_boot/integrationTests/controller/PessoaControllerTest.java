package br.com.rodrigo.rest_with_spring_boot.integrationTests.controller;

import br.com.rodrigo.rest_with_spring_boot.config.TestConfigs;
import br.com.rodrigo.rest_with_spring_boot.integrationTests.testcontainers.AbstractIntegrationTest;
import br.com.rodrigo.rest_with_spring_boot.model.Pessoa;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // definindo o ordenamento dos metodos a serem testados
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PessoaControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Pessoa pessoa;

    //toda essa configuracao no @Before all vai fazer toda a requisicao, chamar o service, o repository , buscar informacoes no BD , retornar informacoes, como se fosse um postman
    @BeforeAll
    public static void setup() {
        //arrange / given
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/pessoas") //qual o caminho da requisicao a ser testada
                .setPort(TestConfigs.SERVER_PORT) // qual a porta onde o teste sera feito
                .addFilter(new RequestLoggingFilter(LogDetail.ALL)) //solicita todos os logs do teste
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL)) //envia todos os logs do teste como resposta
                .build();
        pessoa = new Pessoa("Inaldinho", "Silva",
                "Rua qualquer", "masculino", "inaldinho@email.com");
    }

    @Test
    @Order(1) // Sera o primeiro metodo a ser testado
    @DisplayName("Testes de integracao quando criar uma pessoa deveria retornar um objeto Pessoa")
    void cadastrarPessoaIntegrationTest() throws IOException {
        //Arrange / Given
        String content = given().spec(specification) //dada a especificacao criada acima (specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)//conteudo em formato json
                .body(pessoa)//seta o objeto pessoa no corpo da requisicao
                .when() //Act / When
                .post() // metodo do create
                .then() //assert / Then
                .statusCode(201)//verifica se o status code é 201 created
                .extract()//extraia o  resultado em formato de string
                .body().asString();

        Pessoa pessoaCriada = objectMapper.readValue(content, Pessoa.class); /* o content recebe uma string em formato de json,
         entao uso o object mapper para ler os valores em objeto. */

        pessoa = pessoaCriada; /* estamos atribuindo a resosta do objeto pessoaCriada em pessoa
         para reaproveitarmos os dados em outros metoodos */
        //assert / Then
        assertNotNull(pessoaCriada);
        assertNotNull(pessoaCriada.getId());
        assertNotNull(pessoaCriada.getUltimoNome());
        assertNotNull(pessoaCriada.getEmail());
        assertNotNull(pessoaCriada.getEndereco());
        assertNotNull(pessoaCriada.getGenero());

        assertTrue(pessoaCriada.getId() > 0);
        assertEquals("Inaldinho", pessoaCriada.getPrimeiroNome());
        assertEquals("Silva", pessoaCriada.getUltimoNome());
        assertEquals("Rua qualquer", pessoaCriada.getEndereco());
        assertEquals("masculino", pessoaCriada.getGenero());
        assertEquals("inaldinho@email.com", pessoaCriada.getEmail());
    }

    @Test
    @Order(2) // Sera o segundo metodo a ser testado
    @DisplayName("Testes de integracao quando Atualizar uma pessoa deveria retornar um objeto Pessoa Atualizado")
    void atualizarPessoaIntegrationTest() throws IOException {
        //Arrange / Given
        //atualizando o nome e email da pessoa
        pessoa.setPrimeiroNome("Juquinha");
        pessoa.setEmail("juquinha@bol.com.br");

        String content = given().spec(specification) //dada a especificacao criada acima (specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)//conteudo em formato json
                .pathParam("id", pessoa.getId()) //colocando a variavel id como parametro
                .body(pessoa)//atualiza o objeto pessoa no corpo da requisicao
                .when() //Act / When
                .put("{id}") // metodo do update passando a variavel id como parametro
                .then() //assert / Then
                .statusCode(200)//verifica se o status code é 200 OK
                .extract()//extraia o  resultado em formato de string
                .body().asString();

        Pessoa pessoaAtualizada = objectMapper.readValue(content, Pessoa.class); /* o content recebe uma string em formato de json,
         entao uso o object mapper para ler os valores em objeto. */

        pessoa = pessoaAtualizada; /* estamos atribuindo a resosta do objeto pessoaCriada em pessoa
         para reaproveitarmos os dados em outros metoodos */
        //assert / Then
        assertNotNull(pessoaAtualizada);
        assertNotNull(pessoaAtualizada.getId());
        assertNotNull(pessoaAtualizada.getUltimoNome());
        assertNotNull(pessoaAtualizada.getEmail());
        assertNotNull(pessoaAtualizada.getEndereco());
        assertNotNull(pessoaAtualizada.getGenero());

        assertTrue(pessoaAtualizada.getId() > 0);
        assertEquals("Juquinha", pessoaAtualizada.getPrimeiroNome());
        assertEquals("Silva", pessoaAtualizada.getUltimoNome());
        assertEquals("Rua qualquer", pessoaAtualizada.getEndereco());
        assertEquals("masculino", pessoaAtualizada.getGenero());
        assertEquals("juquinha@bol.com.br", pessoaAtualizada.getEmail());
    }

    @Test
    @Order(3) // Sera o terceiro metodo a ser testado
    @DisplayName("Testes de integracao quando informar o id de uma pessoa deveria retornar um objeto Pessoa")
    void encontrarPessoaPorIdIntegrationTest() throws IOException {
        //Arrange / Given
        String content = given().spec(specification) //dada a especificacao criada acima (specification)
                .pathParam("id", pessoa.getId()) //colocando a variavel id como parametro
                .when() //Act / When
                .get("{id}") // metodo get passando a variavel id como parametro
                .then() //assert / Then
                .statusCode(200)//verifica se o status code é 200 OK
                .extract()//extraia o  resultado em formato de string
                .body().asString();

        Pessoa pessoaEncontrada = objectMapper.readValue(content, Pessoa.class); /* o content recebe uma string em formato de json,
         entao uso o object mapper para ler os valores em objeto. */

        //assert / Then
        assertNotNull(pessoaEncontrada);
        assertNotNull(pessoaEncontrada.getId());
        assertNotNull(pessoaEncontrada.getUltimoNome());
        assertNotNull(pessoaEncontrada.getEmail());
        assertNotNull(pessoaEncontrada.getEndereco());
        assertNotNull(pessoaEncontrada.getGenero());

        assertTrue(pessoaEncontrada.getId() > 0);
        assertEquals("Juquinha", pessoaEncontrada.getPrimeiroNome());
        assertEquals("Silva", pessoaEncontrada.getUltimoNome());
        assertEquals("Rua qualquer", pessoaEncontrada.getEndereco());
        assertEquals("masculino", pessoaEncontrada.getGenero());
        assertEquals("juquinha@bol.com.br", pessoaEncontrada.getEmail());
    }

    @Test
    @Order(4) // Sera o quarto metodo a ser testado
    @DisplayName("Testes de integracao  deveria retornar uma lista de pessoas")
    void listarPessoasIntegrationTest() throws IOException {
        //Arrange / Given
        List<Pessoa> listaDePessoas = new ArrayList<>();
        Pessoa pessoa2 = new Pessoa("Jonathas", "Pereira",
                "Outra rua qualquer", "masculino", "jonatas@uol.com.br");
        //setando no banco de dados a pessoa2:
        given().spec(specification) //dada a especificacao criada acima (specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(pessoa2)
                .when() //Act / When
                .post();

        String content = given().spec(specification) //dada a especificacao criada acima (specification)
                .when() //Act / When
                .get() // metodo get
                .then() //assert / Then
                .statusCode(200)//verifica se o status code é 200 OK
                .extract()//extraia o  resultado em formato de string
                .body().asString();
        List<Pessoa> pessoaEncontradaLista = objectMapper.readValue(content, new TypeReference<List<Pessoa>>() {
        }); /* o content recebe uma string em formato de json,
         entao uso o object mapper para ler os valores em objeto, tendo como segundo parametro uma lista. */

        Pessoa pessoaEncontrada1 = pessoaEncontradaLista.get(0);

        //assert / Then
        assertNotNull(pessoaEncontrada1);
        assertNotNull(pessoaEncontrada1.getId());
        assertNotNull(pessoaEncontrada1.getPrimeiroNome());
        assertNotNull(pessoaEncontrada1.getUltimoNome());
        assertNotNull(pessoaEncontrada1.getEmail());
        assertNotNull(pessoaEncontrada1.getEndereco());
        assertNotNull(pessoaEncontrada1.getGenero());

        assertTrue(pessoaEncontrada1.getId() > 0);
        assertEquals("Juquinha", pessoaEncontrada1.getPrimeiroNome());
        assertEquals("Silva", pessoaEncontrada1.getUltimoNome());
        assertEquals("Rua qualquer", pessoaEncontrada1.getEndereco());
        assertEquals("masculino", pessoaEncontrada1.getGenero());
        assertEquals("juquinha@bol.com.br", pessoaEncontrada1.getEmail());

        Pessoa pessoaEncontrada2 = pessoaEncontradaLista.get(1);

        assertNotNull(pessoaEncontrada2);
        assertNotNull(pessoaEncontrada2.getId());
        assertNotNull(pessoaEncontrada2.getPrimeiroNome());
        assertNotNull(pessoaEncontrada2.getUltimoNome());
        assertNotNull(pessoaEncontrada2.getEmail());
        assertNotNull(pessoaEncontrada2.getEndereco());
        assertNotNull(pessoaEncontrada2.getGenero());

        assertTrue(pessoaEncontrada2.getId() > 0);
        assertEquals("Jonathas", pessoaEncontrada2.getPrimeiroNome());
        assertEquals("Pereira", pessoaEncontrada2.getUltimoNome());
        assertEquals("Outra rua qualquer", pessoaEncontrada2.getEndereco());
        assertEquals("masculino", pessoaEncontrada2.getGenero());
        assertEquals("jonatas@uol.com.br", pessoaEncontrada2.getEmail());
    }
}