package br.com.rodrigo.rest_with_spring_boot.service;

import br.com.rodrigo.rest_with_spring_boot.exception.IdNotFoundException;
import br.com.rodrigo.rest_with_spring_boot.model.Pessoa;
import br.com.rodrigo.rest_with_spring_boot.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {
    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public Pessoa encontrarPessoaPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Id nao encontrado!"));
    }

    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizarPessoa(Long idPessoa, Pessoa pessoa) {
        Pessoa pessoaEntity = pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new IdNotFoundException("Id nao encontrado!"));

        pessoaEntity.setPrimeiroNome(pessoa.getPrimeiroNome());
        pessoaEntity.setUltimoNome(pessoa.getUltimoNome());
        pessoaEntity.setEndereco(pessoa.getEndereco());
        pessoaEntity.setGenero(pessoa.getGenero());

        return pessoaEntity;
    }

    public void deletarPessoa(Long id) {
        Pessoa pessoaEntity = pessoaRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Id nao encontrado!"));
        pessoaRepository.delete(pessoaEntity);
    }
}
