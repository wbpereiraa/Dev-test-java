package br.com.magnumscd.www.api_2.service;

import br.com.magnumscd.www.api_2.entity.FipeCarros;
import br.com.magnumscd.www.api_2.repository.VeiculoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class VeiculoService {

    private final String fipeBaseUrl = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @RabbitListener(queues = "${broker.queue.api2.name}")
    @Transactional
    public void processarMarca(Map<String, Object> marca) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            Long codigoMarca = (Long) marca.get("codigo");
            String nomeMarca = (String) marca.get("nome");


            if (codigoMarca == null || nomeMarca == null) {
                System.err.println("Dados da marca inválidos: " + marca);
                return;
            }

            String modelosUrl = fipeBaseUrl;
            Map<String, Object> resposta = restTemplate.getForObject(modelosUrl, Map.class);

            if (resposta != null && resposta.containsKey("marcas")) {
                List<Map<String, Object>> modelos = (List<Map<String, Object>>) resposta.get("marcas");

                for (Map<String, Object> modelo : modelos) {
                    FipeCarros fipeCarros = new FipeCarros();
                    fipeCarros.setCodigo(codigoMarca);
                    fipeCarros.setMarcas(nomeMarca);

                    veiculoRepository.save(fipeCarros);
                }

                System.out.println("Modelos salvos para a marca: " + nomeMarca);
            } else {
                System.out.println("Nenhum modelo encontrado para a marca: " + nomeMarca);
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar a marca: " + marca + ". Detalhes: " + e.getMessage());
        }
    }
}
