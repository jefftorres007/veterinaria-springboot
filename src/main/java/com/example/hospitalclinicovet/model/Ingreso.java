package com.example.hospitalclinicovet.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.hospitalclinicovet.model.Mascota;
import com.example.hospitalclinicovet.model.enu.EstadoIngreso;

@Entity
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaAltaIngreso;
    private LocalDateTime fechaFinalizacionIngreso;

    @Enumerated(EnumType.STRING)
    private EstadoIngreso estado;

    private String dniPersonaRegistro;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    public Ingreso() {
    }

    public Ingreso(LocalDateTime fechaAltaIngreso, LocalDateTime fechaFinalizacionIngreso, EstadoIngreso estado, String dniPersonaRegistro, Mascota mascota) {
        this.fechaAltaIngreso = fechaAltaIngreso;
        this.fechaFinalizacionIngreso = fechaFinalizacionIngreso;
        this.estado = estado;
        this.dniPersonaRegistro = dniPersonaRegistro;
        this.mascota = mascota;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaAltaIngreso() {
        return fechaAltaIngreso;
    }

    public void setFechaAltaIngreso(LocalDateTime fechaAltaIngreso) {
        this.fechaAltaIngreso = fechaAltaIngreso;
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

    public String getDniPersonaRegistro() {
        return dniPersonaRegistro;
    }

    public void setDniPersonaRegistro(String dniPersonaRegistro) {
        this.dniPersonaRegistro = dniPersonaRegistro;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

}
