package edu.infnet.veiculos.controller;

import edu.infnet.veiculos.model.Veiculo;
import edu.infnet.veiculos.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(VeiculoController.class)
public class UpdateTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoRepository veiculoRepository;

    private Veiculo veiculo1;

    @BeforeEach
    public void setup() {
        veiculo1 = new Veiculo(1L, "Marca A", "Modelo A", 2020);
    }

    @Test
    public void shouldUpdateVeiculoOnValidData() throws Exception {
        // Verify mock setup
        System.out.println("Setting up mock repository for shouldUpdateVeiculoOnValidData");
        given(veiculoRepository.findById(veiculo1.getId())).willReturn(Optional.of(veiculo1));

        Veiculo updatedVeiculo = new Veiculo(1L, "Marca A", "Modelo A atualizado", 2020);
        given(veiculoRepository.save(Mockito.any(Veiculo.class))).willReturn(updatedVeiculo);

        String veiculoJson = "{\"marca\":\"Marca A\",\"modelo\":\"Modelo A atualizado\",\"ano\":2020}";
        System.out.println("Performing PUT request to /veiculos/1 with payload: " + veiculoJson);

        // Check IDs
        System.out.println("Expected ID: " + veiculo1.getId());

        mockMvc.perform(put("/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(veiculoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value(updatedVeiculo.getModelo()));
    }
}