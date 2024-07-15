package com.example.hospitalclinicovet.controller;

import com.example.hospitalclinicovet.model.Ingreso;
import com.example.hospitalclinicovet.model.Mascota;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MascotaController.class)
public class MascotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MascotaService mascotaService;

    @MockBean
    private IngresoService ingresoService;

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
        ingreso.setMascota(mascota);
    }

    @Test
    void testGetAllMascotas() throws Exception {
        List<Mascota> mascotas = Arrays.asList(mascota);
        Mockito.when(mascotaService.getAllMascotas()).thenReturn(mascotas);

        mockMvc.perform(get("/mascota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(mascotas.size()));
    }

    @Test
    void testGetMascotaById() throws Exception {
        Mockito.when(mascotaService.getMascotaById(anyLong())).thenReturn(Optional.of(mascota));

        mockMvc.perform(get("/mascota/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetIngresosByMascota() throws Exception {
        List<Ingreso> ingresos = Arrays.asList(ingreso);
        Mockito.when(mascotaService.getMascotaById(anyLong())).thenReturn(Optional.of(mascota));
        Mockito.when(ingresoService.getAllIngresosByMascota(anyLong())).thenReturn(ingresos);

        mockMvc.perform(get("/mascota/1/ingreso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(ingresos.size()));
    }

    @Test
    void testCreateMascota() throws Exception {
        Mockito.when(mascotaService.saveMascota(any(Mascota.class))).thenReturn(mascota);

        mockMvc.perform(post("/mascota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mascota)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testActivaMascota() throws Exception {
        Mockito.when(mascotaService.getMascotaById(anyLong())).thenReturn(Optional.of(mascota));
        Mockito.when(mascotaService.saveMascota(any(Mascota.class))).thenReturn(mascota);

        mockMvc.perform(put("/mascota/activa/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activa").value(true));
    }

    @Test
    void testDeleteMascota() throws Exception {
        Mockito.when(mascotaService.getMascotaById(anyLong())).thenReturn(Optional.of(mascota));
        Mockito.when(mascotaService.saveMascota(any(Mascota.class))).thenReturn(mascota);

        mockMvc.perform(delete("/mascota/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activa").value(false));
    }
}
