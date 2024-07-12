package com.example.hospitalclinicovet.controller;

import com.example.hospitalclinicovet.model.Ingreso;
import com.example.hospitalclinicovet.model.Mascota;
import com.example.hospitalclinicovet.service.IngresoServiceImpl;
import com.example.hospitalclinicovet.service.MascotaService;
import com.example.hospitalclinicovet.service.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mascota")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;
    @Autowired
    private IngresoService ingresoService;


    @GetMapping
    public List<Mascota> getAllMascotas() {
        return mascotaService.getAllMascotas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.getMascotaById(id);
        if (mascota.isPresent()) {
            return ResponseEntity.ok(mascota.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/ingreso")
    public ResponseEntity<List<Ingreso>> getIngresosByMascota(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.getMascotaById(id);
        if (mascota.isPresent()) {
            List<Ingreso> ingresos = ingresoService.getAllIngresosByMascota(id);
            return ResponseEntity.ok(ingresos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Mascota createMascota(@RequestBody Mascota mascota) {
        return mascotaService.saveMascota(mascota);
    }

    @PutMapping("/activa/{id}")
    public ResponseEntity<Mascota> activaMascota(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.getMascotaById(id);
        if (mascota.isPresent()) {
            Mascota mascotaToUpdate = mascota.get();
            mascotaToUpdate.setActiva(true);
            Mascota updatedMascota = mascotaService.saveMascota(mascotaToUpdate);
            return ResponseEntity.ok(updatedMascota);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Mascota> updateMascota(@PathVariable Long id, @RequestBody Mascota mascotaDetails) {
        Optional<Mascota> mascota = mascotaService.getMascotaById(id);
        if (mascota.isPresent()) {
            Mascota mascotaToUpdate = mascota.get();
            mascotaToUpdate.setEspecie(mascotaDetails.getEspecie());
            mascotaToUpdate.setRaza(mascotaDetails.getRaza());
            mascotaToUpdate.setEdad(mascotaDetails.getEdad());
            mascotaToUpdate.setCodigoIdentificacion(mascotaDetails.getCodigoIdentificacion());
            mascotaToUpdate.setDniResponsable(mascotaDetails.getDniResponsable());
            Mascota updatedMascota = mascotaService.saveMascota(mascotaToUpdate);
            return ResponseEntity.ok(updatedMascota);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.getMascotaById(id);
        if (mascota.isPresent()) {
            mascotaService.deleteMascota(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
