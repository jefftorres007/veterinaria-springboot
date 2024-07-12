package com.example.hospitalclinicovet.service;

import com.example.hospitalclinicovet.model.Mascota;

import java.util.List;
import java.util.Optional;

public interface MascotaService {
    List<Mascota> getAllMascotas();
    Optional<Mascota> getMascotaById(Long id);
    Mascota saveMascota(Mascota mascota);
    void deleteMascota(Long id);
}
