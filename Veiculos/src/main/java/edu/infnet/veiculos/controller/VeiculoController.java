package edu.infnet.veiculos.controller;

import edu.infnet.veiculos.model.Veiculo;
import edu.infnet.veiculos.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public List<Veiculo> listar() {
        System.out.println("GET /veiculos called");
        return veiculoRepository.findAll();
    }

    @PostMapping
    public Veiculo adicionar(@RequestBody Veiculo veiculo) {
        System.out.println("POST /veiculos called with: " + veiculo);
        return veiculoRepository.save(veiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> atualizar(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        System.out.println("PUT /veiculos/" + id + " called with: " + veiculo);
        Optional<Veiculo> existingVeiculo = veiculoRepository.findById(id);
        System.out.println("Existing vehicle: " + existingVeiculo);
        return existingVeiculo
                .map(record -> {
                    record.setMarca(veiculo.getMarca());
                    record.setModelo(veiculo.getModelo());
                    record.setAno(veiculo.getAno());
                    Veiculo updated = veiculoRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        System.out.println("DELETE /veiculos/" + id + " called");
        return veiculoRepository.findById(id)
                .map(record -> {
                    veiculoRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<?> removerTodos() {
        System.out.println("DELETE /veiculos called");
        veiculoRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}