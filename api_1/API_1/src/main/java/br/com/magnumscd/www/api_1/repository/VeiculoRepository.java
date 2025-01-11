package br.com.magnumscd.www.api_1.repository;

import br.com.magnumscd.www.api_1.entity.FipeCarros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<FipeCarros, Long> {

    List<FipeCarros> findByMarcas(String marcas);

}
