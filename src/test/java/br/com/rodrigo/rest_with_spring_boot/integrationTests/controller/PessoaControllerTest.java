package br.com.rodrigo.rest_with_spring_boot.integrationTests.controller;

import br.com.rodrigo.rest_with_spring_boot.config.TestConfigs;
import br.com.rodrigo.rest_with_spring_boot.integrationTests.testcontainers.AbstractIntegrationTest;
import br.com.rodrigo.rest_with_spring_boot.model.Pessoa;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PessoaControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Pessoa pessoa;

    //toda essa configuracao no @Before all vai fazer toda a requisicao, chamar o service, o repository , buscar informacoes no BD , retornar informacoes, como se fosse um postman
    @BeforeAll
    public void setup() {
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
}