package com.example.hospitalclinicovet.repository;

import com.example.hospitalclinicovet.model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
    List<Ingreso> findByMascotaId(Long idMascota);
}
