package edu.infnet.veiculos.repository;

import edu.infnet.veiculos.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}