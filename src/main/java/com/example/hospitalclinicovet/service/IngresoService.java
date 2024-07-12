package com.example.hospitalclinicovet.service;

import com.example.hospitalclinicovet.dto.IngresoDTO;
import com.example.hospitalclinicovet.model.Ingreso;

import java.util.List;
import java.util.Optional;

public interface IngresoService {
    List<Ingreso> getAllIngresos();
    Optional<Ingreso> getIngresoById(Long id);
    Ingreso saveIngreso(Ingreso ingreso);
    Ingreso createIngreso(IngresoDTO ingresoDTO);
    void bajaIngreso(Long id);
    List<Ingreso> getAllIngresosByMascota(Long idMascota);
}
