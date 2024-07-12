


package com.example.hospitalclinicovet.repository;

import com.example.hospitalclinicovet.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}