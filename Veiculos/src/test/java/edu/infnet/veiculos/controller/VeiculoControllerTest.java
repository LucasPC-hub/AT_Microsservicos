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
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
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
                .andExpect(jsonPath("$", hasSize(2)))
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
                .andExpect(jsonPath("$.marca").value(veiculo1.getMarca()));
    }

    @Test
    public void testAtualizar() throws Exception {
        given(veiculoRepository.findById(veiculo1.getId())).willReturn(java.util.Optional.of(veiculo1));
        given(veiculoRepository.save(Mockito.any(Veiculo.class))).willReturn(veiculo1);

        String veiculoJson = "{\"marca\":\"Marca A\",\"modelo\":\"Modelo A atualizado\",\"ano\":2020}";
        mockMvc.perform(put("/veiculos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(veiculoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelo").value("Modelo A atualizado"));
    }

    @Test
    public void testRemover() throws Exception {
        given(veiculoRepository.findById(veiculo1.getId())).willReturn(java.util.Optional.of(veiculo1));

        mockMvc.perform(delete("/veiculos/1"))
                .andExpect(status().isOk());

        verify(veiculoRepository, times(1)).deleteById(veiculo1.getId());
    }

    @Test
    public void testRemoverTodos() throws Exception {
        mockMvc.perform(delete("/veiculos"))
                .andExpect(status().isOk());

        verify(veiculoRepository, times(1)).deleteAll();
    }
}