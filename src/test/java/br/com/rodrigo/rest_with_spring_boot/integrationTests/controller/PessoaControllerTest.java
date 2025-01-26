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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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
        assertEquals("Silva", pessoaCriada.getUltimoNome());
        assertEquals("Rua qualquer", pessoaCriada.getEndereco());
        assertEquals("masculino", pessoaCriada.getGenero());
        assertEquals("inaldinho@email.com", pessoaCriada.getEmail());
    }
}