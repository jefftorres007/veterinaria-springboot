package com.example.hospitalclinicovet.dto;

import com.example.hospitalclinicovet.model.enu.EstadoIngreso;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

public class IngresoDTO {
    private Long mascotaId;
    private LocalDateTime fechaAltaIngreso;
    private LocalDateTime fechaFinalizacionIngreso;
    private String dniPersonaIngreso;
    @Enumerated(EnumType.STRING)
    private EstadoIngreso estado;

    // Getters y Setters
    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public LocalDateTime getFechaAltaIngreso() {
        return fechaAltaIngreso;
    }

    public void setFechaAltaIngreso(LocalDateTime fechaAltaIngreso) {
        this.fechaAltaIngreso = fechaAltaIngreso;
    }

    public String getDniPersonaIngreso() {
        return dniPersonaIngreso;
    }

    public void setDniPersonaIngreso(String dniPersonaIngreso) {
        this.dniPersonaIngreso = dniPersonaIngreso;
    }

    public LocalDateTime getFechaFinalizacionIngreso() {
        return fechaFinalizacionIngreso;
    }

    public void setFechaFinalizacionIngreso(LocalDateTime fechaFinalizacionIngreso) {
        this.fechaFinalizacionIngreso = fechaFinalizacionIngreso;
    }

    public EstadoIngreso getEstado() {
        return estado;
    }

    public void setEstado(EstadoIngreso estado) {
        this.estado = estado;
    }
}
