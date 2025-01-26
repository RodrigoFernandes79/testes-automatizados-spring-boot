package br.com.rodrigo.rest_with_spring_boot.integrationTests.swagger;

import br.com.rodrigo.rest_with_spring_boot.config.TestConfigs;
import br.com.rodrigo.rest_with_spring_boot.integrationTests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

//pra rodar esse teste tem que inicializar a  aplicacao (run RestWithSpringBootApplication.java)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8888")
//na porta 8888
public class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @DisplayName("Teste Junit deveria mostrar Swagger UI page")
    @Test
    void testDeveriaMostrarPaginaDoSwaggerUi() {
        //Arrange / Given
        String content = given()
                .basePath("/swagger-ui/index.html")//url da pagina do swagger onde acesso os endpoints da api
                .port(TestConfigs.SERVER_PORT) // classe de configuracao onde configuramos a porta
                .when() //Act / When
                .get()
                .then() //assert / Then
                .statusCode(200)//verifica se o status code é 200
                .extract()//extraia o  resultado em formato de string
                .body().asString();
        assertTrue(content.contains("Swagger UI"));

    }
}
