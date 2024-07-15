package com.example.hospitalclinicovet.controller;

import com.example.hospitalclinicovet.dto.IngresoDTO;
import com.example.hospitalclinicovet.exception.VeterinariaException;
import com.example.hospitalclinicovet.model.Ingreso;
import com.example.hospitalclinicovet.model.Mascota;
import com.example.hospitalclinicovet.model.enu.EstadoIngreso;
import com.example.hospitalclinicovet.service.IngresoService;
import com.example.hospitalclinicovet.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingreso")
public class IngresoController {
    @Autowired
    private IngresoService ingresoService;
    @Autowired
    private MascotaService mascotaService;

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

    @PutMapping("/{idIngreso}")
    public ResponseEntity<Ingreso> finalizaIngreso(@PathVariable Long idIngreso, @RequestBody IngresoDTO ingresoDetails) {
        Optional<Ingreso> ingreso = ingresoService.getIngresoById(idIngreso);
        Optional<Mascota> mascota = mascotaService.getMascotaById(ingresoDetails.getMascotaId());

        //throw new VeterinariaException("aquii");
        //return ResponseEntity.notFound().build();

        // Validaciones
        if (!ingreso.isPresent()) {
            throw new VeterinariaException(MessageFormat.format("Ingreso {0} no encontrado", idIngreso));
        }if (!mascota.isPresent()) {
            throw new VeterinariaException(MessageFormat.format("Mascota {0} no encontrada", ingresoDetails.getMascotaId()));
        }

        Ingreso ingresoObj = ingreso.get();
        Mascota mascotaObj = mascota.get();

        if (!mascotaObj.getActiva()) {
            throw new VeterinariaException(MessageFormat.format("La mascota {0} no está activa, no puede finalizar el ingreso",mascotaObj.getId()));
        }
        if (!ingresoDetails.getDniPersonaIngreso().equalsIgnoreCase( mascotaObj.getDniResponsable() )) {
            throw new VeterinariaException(MessageFormat.format("El documento {0} no corresponde a la mascota {1}",ingresoDetails.getDniPersonaIngreso(),mascotaObj.getDniResponsable()));
        }
        if (ingresoDetails.getEstado() == EstadoIngreso.FINALIZADO) {
            if (ingresoDetails.getFechaFinalizacionIngreso() == null) {
                throw new VeterinariaException("Para finalizar este ingreso debe asignar una fecha de fin del ingreso");
            }
        }

        ingresoObj.setEstado(EstadoIngreso.FINALIZADO);
        ingresoObj.setFechaFinalizacionIngreso(ingresoDetails.getFechaFinalizacionIngreso());

        Ingreso updatedIngreso = ingresoService.saveIngreso(ingresoObj);
        return ResponseEntity.ok(updatedIngreso);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Ingreso> deleteIngreso(@PathVariable Long id) {
        Optional<Ingreso> ingreso = ingresoService.getIngresoById(id);

        if (!ingreso.isPresent()) {
            throw new VeterinariaException("Ingreso no encontrado");
        }

        Optional<Mascota> mascota = mascotaService.getMascotaById(ingreso.get().getMascota().getId());
        Mascota mascotaObj = mascota.get();
        if (!mascotaObj.getActiva()) {
            throw new VeterinariaException(MessageFormat.format("La mascota {0} no está activa, no se puede realizar la operación",mascotaObj.getId()));
        }

        ingresoService.bajaIngreso(id);
        return ResponseEntity.ok(ingreso.get());

    }
}
