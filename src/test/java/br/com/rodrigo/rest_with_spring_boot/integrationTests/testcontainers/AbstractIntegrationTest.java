package br.com.rodrigo.rest_with_spring_boot.integrationTests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {
    //criamos o container mysql usando a docker image do mysql na versao 8.0.28. Esse container vai ser instanciado atraves dessa configuracao
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.28");

    private static void startContainers() {
        Startables.deepStart(Stream.of(mysql)).join();
    }

    //criando as configuracoes do mysql
    private static Map<String, String> createConnectionConfiguration() {
        return Map.of("spring.datasource.url", mysql.getJdbcUrl(),
                "spring.datasource.username", mysql.getUsername(),
                "spring.datasource.password", mysql.getPassword()
        );
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers(); //chama o metodo para iniciar o mysql
            ConfigurableEnvironment environment = applicationContext.getEnvironment(); //obtem o contexto do spring que esta inicializando
            MapPropertySource testContainers =
                    new MapPropertySource("testContainers", (Map) createConnectionConfiguration());
            environment.getPropertySources().addFirst(testContainers);
        }
    }
}
