package br.com.magnumscd.www.api_1.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class VeiculoProducer {

    @Value("${api.fipe.key}")
    private String apiKey;

    private final RabbitTemplate rabbitTemplate;
    private final String baseUrl = "https://parallelum.com.br/fipe/api/v1/carros/marcas";

    public VeiculoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void carregarDadosFipe() {

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://parallelum.com.br/fipe/api/v1/carros/marcas";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseTeste = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        System.out.println("Resposta da API: " + responseTeste.getBody());

        try {
            ResponseEntity<List> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, List.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, String>> marcas = response.getBody();

                for (Map<String, String> marca : marcas) {
                    rabbitTemplate.convertAndSend("${broker.queue.api2.name}", marca);
                }

                System.out.println("Marcas carregadas e enviadas para a fila com sucesso.");
            } else {
                throw new RuntimeException("Falha ao buscar marcas. Resposta: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar marcas da FIPE: " + e.getMessage(), e);
        }
    }
}
