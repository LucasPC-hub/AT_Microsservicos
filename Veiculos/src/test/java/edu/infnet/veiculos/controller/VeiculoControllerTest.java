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

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VeiculoController.class)
public class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoRepository veiculoRepository;

    private Veiculo veiculo1;
    private Veiculo veiculo2;

    @BeforeEach
    public void setup() {
        veiculo1 = new Veiculo(1L, "Marca A", "Modelo A", 2020);
        veiculo2 = new Veiculo(2L, "Marca B", "Modelo B", 2021);
    }

    @Test
    public void testListar() throws Exception {
        given(veiculoRepository.findAll()).willReturn(Arrays.asList(veiculo1, veiculo2));

        mockMvc.perform(get("/veiculos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].marca").value(veiculo1.getMarca()))
                .andExpect(jsonPath("$[1].marca").value(veiculo2.getMarca()));
    }

    @Test
    public void testAdicionar() throws Exception {
        given(veiculoRepository.save(Mockito.any(Veiculo.class))).willReturn(veiculo1);

        String veiculoJson = "{\"marca\":\"Marca A\",\"modelo\":\"Modelo A\",\"ano\":2020}";
        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(veiculoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value(veiculo1.getMarca()))
                .andExpect(jsonPath("$.modelo").value(veiculo1.getModelo()))
                .andExpect(jsonPath("$.ano").value(veiculo1.getAno()));
    }

    @Test
    public void shouldUpdateVeiculoOnValidData() throws Exception {
        System.out.println("Setting up mock repository for shouldUpdateVeiculoOnValidData");
        given(veiculoRepository.findById(veiculo1.getId())).willReturn(Optional.of(veiculo1));
        Veiculo updatedVeiculo = new Veiculo(1L, "Marca A", "Modelo A atualizado", 2020);
        given(veiculoRepository.save(Mockito.any(Veiculo.class))).willReturn(updatedVeiculo);

        String veiculoJson = "{\"marca\":\"Marca A\",\"modelo\":\"Modelo A atualizado\",\"ano\":2020}";
        System.out.println("Performing PUT request to /veiculos/1 with payload: " + veiculoJson);
        mockMvc.perform(put("/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(veiculoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value(updatedVeiculo.getModelo()));
    }

    @Test
    public void shouldNotUpdateVeiculoOnInvalidId() throws Exception {
        Veiculo existingVeiculo = new Veiculo(1L, "Marca A", "Modelo A", 2020);
        given(veiculoRepository.findById(existingVeiculo.getId())).willReturn(Optional.empty());

        String veiculoJson = "{\"marca\":\"Marca A\",\"modelo\":\"Modelo A atualizado\",\"ano\":2020}";
        mockMvc.perform(put("/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(veiculoJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemover() throws Exception {
        given(veiculoRepository.findById(1L)).willReturn(Optional.of(veiculo1));

        mockMvc.perform(delete("/veiculos/1"))
                .andExpect(status().isOk());

        verify(veiculoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldNotUpdateVeiculoOnChangeInNonUpdatableField() throws Exception {
        Veiculo existingVeiculo = new Veiculo(1L, "Marca A", "Modelo A", 2020);
        given(veiculoRepository.findById(existingVeiculo.getId())).willReturn(Optional.of(existingVeiculo));
        Veiculo updatedVeiculo = new Veiculo(1L, "Marca A Updated", "Modelo A", 2020);
        given(veiculoRepository.save(Mockito.any(Veiculo.class))).willReturn(updatedVeiculo);

        String veiculoJson = "{\"marca\":\"Marca A Updated\",\"modelo\":\"Modelo A\",\"ano\":2020}";
        mockMvc.perform(put("/veiculos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(veiculoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value(existingVeiculo.getMarca()));
    }

    @Test
    public void testRemoverTodos() throws Exception {
        mockMvc.perform(delete("/veiculos"))
                .andExpect(status().isOk());

        verify(veiculoRepository, times(1)).deleteAll();
    }
}
