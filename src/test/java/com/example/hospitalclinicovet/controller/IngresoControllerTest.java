package com.example.hospitalclinicovet.controller;

import com.example.hospitalclinicovet.dto.IngresoDTO;
import com.example.hospitalclinicovet.model.Ingreso;
import com.example.hospitalclinicovet.model.Mascota;
import com.example.hospitalclinicovet.model.enu.EstadoIngreso;
import com.example.hospitalclinicovet.service.IngresoService;
import com.example.hospitalclinicovet.service.MascotaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngresoController.class)
public class IngresoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngresoService ingresoService;

    @MockBean
    private MascotaService mascotaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Mascota mascota;
    private Ingreso ingreso;

    @BeforeEach
    void setUp() {
        mascota = new Mascota();
        mascota.setId(1L);
        mascota.setEspecie("Canino");
        mascota.setRaza("Labrador");
        mascota.setEdad(5);
        mascota.setCodigoIdentificacion("A123");
        mascota.setDniResponsable("12345678A");
        mascota.setActiva(true);

        ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setFechaAltaIngreso(LocalDateTime.now());
        ingreso.setEstado(EstadoIngreso.ALTA);
        ingreso.setMascota(mascota);
        ingreso.setDniPersonaRegistro("12345678A");
    }

    @Test
    void testGetAllIngresos() throws Exception {
        mockMvc.perform(get("/ingreso"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetIngresoById() throws Exception {
        Mockito.when(ingresoService.getIngresoById(anyLong())).thenReturn(Optional.of(ingreso));

        mockMvc.perform(get("/ingreso/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCreateIngreso() throws Exception {
        IngresoDTO ingresoDTO = new IngresoDTO();
        ingresoDTO.setMascotaId(1L);
        ingresoDTO.setFechaAltaIngreso(LocalDateTime.now());
        ingresoDTO.setDniPersonaIngreso("12345678A");

        Mockito.when(mascotaService.getMascotaById(anyLong())).thenReturn(Optional.of(mascota));
        Mockito.when(ingresoService.createIngreso(any(IngresoDTO.class))).thenReturn(ingreso);

        mockMvc.perform(post("/ingreso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingresoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testFinalizaIngreso() throws Exception {
        IngresoDTO ingresoDTO = new IngresoDTO();
        ingresoDTO.setMascotaId(1L);
        ingresoDTO.setFechaFinalizacionIngreso(LocalDateTime.now());
        ingresoDTO.setEstado(EstadoIngreso.FINALIZADO);
        ingresoDTO.setDniPersonaIngreso("12345678A");

        Mockito.when(ingresoService.getIngresoById(anyLong())).thenReturn(Optional.of(ingreso));
        Mockito.when(mascotaService.getMascotaById(anyLong())).thenReturn(Optional.of(mascota));
        Mockito.when(ingresoService.saveIngreso(any(Ingreso.class))).thenReturn(ingreso);

        mockMvc.perform(put("/ingreso/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingresoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("FINALIZADO"));
    }

    @Test
    void testDeleteIngreso() throws Exception {
        Mockito.when(ingresoService.getIngresoById(anyLong())).thenReturn(Optional.of(ingreso));
        Mockito.when(mascotaService.getMascotaById(anyLong())).thenReturn(Optional.of(mascota));

        mockMvc.perform(delete("/ingreso/1"))
                .andExpect(status().isOk());
    }
}
