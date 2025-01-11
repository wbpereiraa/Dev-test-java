package br.com.magnumscd.www.api_2.consumers;

import br.com.magnumscd.www.api_2.entity.FipeCarros;
import br.com.magnumscd.www.api_2.repository.VeiculoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class VeiculoConsumer {

    @Autowired
    private VeiculoRepository veiculoRepository;

    private final String fipeBaseUrl = "https://parallelum.com.br/fipe/api/v1/carros/marcas";

    @RabbitListener(queues = "${broker.queue.api2.name}")
    public void processarMarca(Map<String, Object> marca) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String codigoMarca = (String) marca.get("codigo");

            if (codigoMarca == null || codigoMarca.isEmpty()) {
                System.err.println("Marca inválida ou sem código: " + marca);
                return;
            }

            String modelosUrl = fipeBaseUrl;
            List<Map<String, Object>> marcas = restTemplate.getForObject(modelosUrl, List.class);

            if (marcas != null && !marcas.isEmpty()) {
                for (Map<String, Object> modelo : marcas) {
                    String codigo = (String) modelo.get("codigo");
                    String marcaVeiculo = (String) modelo.get("nome");
                    String modeloVeiculo = (String) modelo.get("nome");
                    String observacoes = (String) modelo.get("nome");

                    FipeCarros fipeCarros = new FipeCarros();
                    fipeCarros.setAnos(codigo);
                    fipeCarros.setModelos(modeloVeiculo);
                    fipeCarros.setMarcas(marcaVeiculo);
                    fipeCarros.setObservacoes(observacoes);
                    fipeCarros.setObservacoes("Teste");

                    veiculoRepository.save(fipeCarros);
                    System.out.println("Marca salva com sucesso: " + fipeCarros);

                }
            } else {
                System.out.println("Nenhum modelo encontrado para a marca: " + marca.get("nome"));
            }

        } catch (Exception e) {
            System.err.println("Erro ao processar a marca: " + marca + ". Detalhes: " + e.getMessage());
        }
    }
}

