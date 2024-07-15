package com.example.hospitalclinicovet.dto;

import java.time.LocalDateTime;

public class IngresoDTO {
    private Long mascotaId;
    private LocalDateTime fechaAltaIngreso;
    private LocalDateTime fechaFinalizacionIngreso;
    private String dniPersonaRegistro;


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

    public String getDniPersonaRegistro() {
        return dniPersonaRegistro;
    }

    public void setDniPersonaRegistro(String dniPersonaRegistro) {
        this.dniPersonaRegistro = dniPersonaRegistro;
    }

    public LocalDateTime getFechaFinalizacionIngreso() {
        return fechaFinalizacionIngreso;
    }

    public void setFechaFinalizacionIngreso(LocalDateTime fechaFinalizacionIngreso) {
        this.fechaFinalizacionIngreso = fechaFinalizacionIngreso;
    }
}
