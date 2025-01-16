package br.com.rodrigo.rest_with_spring_boot.repository;

import br.com.rodrigo.rest_with_spring_boot.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
