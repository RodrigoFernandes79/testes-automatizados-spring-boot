package br.com.rodrigo.rest_with_spring_boot.repository;

import br.com.rodrigo.rest_with_spring_boot.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByEmail(String email);

    //Testes Spring Data JPA custom query method Usando JPQL com named parameters
    @Query("SELECT p from Pessoa p where p.primeiroNome = :primeiroNome AND p.ultimoNome = :ultimoNome")
    Pessoa consultaPorNomeSobrenome(
            @Param("primeiroNome") String primeiroNome,
            @Param("ultimoNome") String ultimoNome);

    //Testando Spring Data JPA custom Native query com Named parameters
    @Query(value = "SELECT * from pessoa p where p.primeiro_nome =:primeiroNome AND p.ultimo_nome =:ultimoNome", nativeQuery = true)
    Pessoa consultaSQLNativoPorNomeSobrenome(
            @Param("primeiroNome") String primeiroNome,
            @Param("ultimoNome") String ultimoNome);

}
