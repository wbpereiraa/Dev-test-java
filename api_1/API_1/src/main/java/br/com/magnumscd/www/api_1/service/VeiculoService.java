package br.com.magnumscd.www.api_1.service;

import br.com.magnumscd.www.api_1.entity.FipeCarros;
import br.com.magnumscd.www.api_1.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public List<FipeCarros> buscarTodasMarcas() {
        return veiculoRepository.findAll();
    }

    public List<FipeCarros> buscarVeiculosPorMarca(String marca) {
        return veiculoRepository.findByMarcas(marca);
    }

    public FipeCarros salvarOuAtualizarVeiculo(Long id, FipeCarros fipeCarrosAtualizado) {
        Optional<FipeCarros> veiculoExistente = veiculoRepository.findById(id);

        if (veiculoExistente.isPresent()) {
            FipeCarros fipeCarros = veiculoExistente.get();
            fipeCarros.setModelos(fipeCarrosAtualizado.getModelos());
            fipeCarros.setMarcas(fipeCarrosAtualizado.getMarcas());
            fipeCarros.setObservacoes(fipeCarrosAtualizado.getObservacoes());
            return veiculoRepository.save(fipeCarros);
        } else {
            throw new IllegalArgumentException("Veículo com ID " + id + " não encontrado.");
        }
    }
}

