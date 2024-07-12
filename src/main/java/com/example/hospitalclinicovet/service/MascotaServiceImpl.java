package com.example.hospitalclinicovet.service;

import com.example.hospitalclinicovet.model.Mascota;
import com.example.hospitalclinicovet.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaServiceImpl implements MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Override
    public List<Mascota> getAllMascotas() {
        return mascotaRepository.findAll();
    }

    @Override
    public Optional<Mascota> getMascotaById(Long id) {
        return mascotaRepository.findById(id);
    }

    @Override
    public Mascota saveMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    @Override
    public void deleteMascota(Long id) {
        mascotaRepository.deleteById(id);
    }
}
