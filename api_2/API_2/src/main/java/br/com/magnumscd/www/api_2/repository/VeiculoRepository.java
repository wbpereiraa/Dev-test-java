package br.com.magnumscd.www.api_2.repository;

import br.com.magnumscd.www.api_2.entity.FipeCarros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<FipeCarros, Long> {
}
