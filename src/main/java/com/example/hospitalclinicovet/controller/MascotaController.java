package com.example.hospitalclinicovet.controller;

import com.example.hospitalclinicovet.model.Ingreso;
import com.example.hospitalclinicovet.model.Mascota;
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
        if (!mascota.isPresent()) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(mascota.get());
    }

    @GetMapping("/{id}/ingreso")
    public ResponseEntity<List<Ingreso>> getIngresosByMascota(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.getMascotaById(id);
        if (!mascota.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<Ingreso> ingresos = ingresoService.getAllIngresosByMascota(id);
        return ResponseEntity.ok(ingresos);
    }

    @PostMapping
    public Mascota createMascota(@RequestBody Mascota mascota) {
        return mascotaService.saveMascota(mascota);
    }

    @PutMapping("/activa/{id}")
    public ResponseEntity<Mascota> activaMascota(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.getMascotaById(id);
        if (!mascota.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Mascota mascotaToActivate = mascota.get();
        mascotaToActivate.setActiva(true);
        Mascota updatedMascota = mascotaService.saveMascota(mascotaToActivate);
        return ResponseEntity.ok(updatedMascota);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Mascota> deleteMascota(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.getMascotaById(id);
        if (!mascota.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Mascota mascotaToDesactivate = mascota.get();
        mascotaToDesactivate.setActiva(false);
        Mascota updatedMascota = mascotaService.saveMascota(mascotaToDesactivate);
        return ResponseEntity.ok(updatedMascota);

    }


}
