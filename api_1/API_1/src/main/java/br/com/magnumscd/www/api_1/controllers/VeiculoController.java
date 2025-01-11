package br.com.magnumscd.www.api_1.controllers;

import br.com.magnumscd.www.api_1.entity.FipeCarros;
import br.com.magnumscd.www.api_1.producers.VeiculoProducer;
import br.com.magnumscd.www.api_1.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoProducer veiculoProducer;

    @Autowired
    private VeiculoService veiculoService;

    @Operation(summary = "Carga inicial da lista de veículos na fila.", description = "Carrega a lista de veículos para fila.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de veículos carregada com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FipeCarros.class))),
            @ApiResponse(responseCode = "204", description = "Lista de veículo não carregada.")
    })
    @GetMapping("/carga-inicial")
    public ResponseEntity<?> carregarDadosIniciais() {
        try {
            veiculoProducer.carregarDadosFipe();
            return ResponseEntity.ok("Dados carregados com sucesso na fila!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao carregar dados da FIPE: " + e.getMessage());
        }
    }

    @Operation(summary = "Buscar todos os veículos de todas as marcas.", description = "Retorna uma lista de veículos de todas as marcas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de veículos retornada com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FipeCarros.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum veículo encontrado.")
    })
    @GetMapping("/todas-marcas")
    public ResponseEntity<List<FipeCarros>> buscarMarcas() {
        List<FipeCarros> marcas = veiculoService.buscarTodasMarcas();
        if (marcas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(marcas);
    }

    @Operation(summary = "Buscar todos os veículos por marca.", description = "Retorna uma lista de veículos com base na marca fornecida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de veículos retornada com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FipeCarros.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum veículo encontrado para a marca.")
    })
    @GetMapping("/por-marca")
    public ResponseEntity<List<FipeCarros>> buscarPorMarca(@RequestParam String marca) {
        List<FipeCarros> carros = veiculoService.buscarVeiculosPorMarca(marca);
        if (carros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }

    @Operation(summary = "Atualizar informações de um veículo.", description = "Atualiza o modelo e observações de um veículo baseado no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FipeCarros.class))),
            @ApiResponse(responseCode = "404", description = "Veículo não encontrado.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FipeCarros> atualizarVeiculo(
            @PathVariable Long id,
            @RequestBody FipeCarros fipeCarrosAtualizado) {
        try {
            FipeCarros fipeCarros = veiculoService.salvarOuAtualizarVeiculo(id, fipeCarrosAtualizado);
            return ResponseEntity.ok(fipeCarros);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

