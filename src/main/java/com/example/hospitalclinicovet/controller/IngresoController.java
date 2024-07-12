package com.example.hospitalclinicovet.controller;

import com.example.hospitalclinicovet.dto.IngresoDTO;
import com.example.hospitalclinicovet.exception.VeterinariaException;
import com.example.hospitalclinicovet.model.Ingreso;
import com.example.hospitalclinicovet.model.enu.EstadoIngreso;
import com.example.hospitalclinicovet.service.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingreso")
public class IngresoController {
    @Autowired
    private IngresoService ingresoService;

    @GetMapping
    public List<Ingreso> getAllIngresos() {
        return ingresoService.getAllIngresos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingreso> getIngresoById(@PathVariable Long id) {
        Optional<Ingreso> ingreso = ingresoService.getIngresoById(id);
        if (ingreso.isPresent()) {
            return ResponseEntity.ok(ingreso.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Ingreso> createIngreso(@RequestBody IngresoDTO ingresoDTO) {
        Ingreso nuevoIngreso = ingresoService.createIngreso(ingresoDTO);
        return ResponseEntity.ok(nuevoIngreso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingreso> updateIngreso(@PathVariable Long id, @RequestBody Ingreso ingresoDetails) {
        Optional<Ingreso> ingreso = ingresoService.getIngresoById(id);

        // Validaciones
        if (!ingreso.isPresent()) {
            throw new VeterinariaException("Ingreso no encontrado");
        }

        Ingreso ingresoToUpdate = ingreso.get();

        // Aplicar las validaciones antes de actualizar los valores
        if (ingresoDetails.getEstado() == EstadoIngreso.FINALIZADO) {
            if (ingresoDetails.getFechaFinalizacionIngreso() == null) {
                throw new VeterinariaException("Para finalizar este ingreso debe asignar una fecha de fin del ingreso");
            } else if (ingresoDetails.getFechaFinalizacionIngreso().isBefore(ingresoDetails.getFechaAltaIngreso())) {
                throw new VeterinariaException("La fecha del fin del ingreso no puede ser anterior a la de registro");
            }
        }

        ingresoToUpdate.setFechaAltaIngreso(ingresoDetails.getFechaAltaIngreso());
        ingresoToUpdate.setFechaFinalizacionIngreso(ingresoDetails.getFechaFinalizacionIngreso());
        ingresoToUpdate.setEstado(ingresoDetails.getEstado());
        ingresoToUpdate.setMascota(ingresoDetails.getMascota());
        ingresoToUpdate.setDniPersonaRegistro(ingresoDetails.getDniPersonaRegistro());
        Ingreso updatedIngreso = ingresoService.saveIngreso(ingresoToUpdate);
        return ResponseEntity.ok(updatedIngreso);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Ingreso> deleteIngreso(@PathVariable Long id) {
        Optional<Ingreso> ingreso = ingresoService.getIngresoById(id);
        if (!ingreso.isPresent()) {
            throw new VeterinariaException("Ingreso no encontrado");
        }
        ingresoService.bajaIngreso(id);
        return ResponseEntity.ok(ingreso.get());

    }
}
