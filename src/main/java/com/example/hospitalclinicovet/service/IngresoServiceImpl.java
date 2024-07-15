package com.example.hospitalclinicovet.service;

import com.example.hospitalclinicovet.dto.IngresoDTO;
import com.example.hospitalclinicovet.exception.VeterinariaException;
import com.example.hospitalclinicovet.model.Ingreso;
import com.example.hospitalclinicovet.model.Mascota;
import com.example.hospitalclinicovet.model.enu.EstadoIngreso;
import com.example.hospitalclinicovet.repository.IngresoRepository;
import com.example.hospitalclinicovet.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngresoServiceImpl implements IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Override
    public List<Ingreso> getAllIngresos() {
        return ingresoRepository.findAll();
    }
    @Override
    public List<Ingreso> getAllIngresosByMascota(Long idMascota) {
        return ingresoRepository.findByMascotaId(idMascota);
    }

    @Override
    public Optional<Ingreso> getIngresoById(Long id) {
        return ingresoRepository.findById(id);
    }

    @Override
    public Ingreso saveIngreso(Ingreso ingreso) {
        return ingresoRepository.save(ingreso);
    }


    @Override
    public void bajaIngreso(Long id) {
        Optional<Ingreso> ingreso = ingresoRepository.findById(id);
        ingreso.get().setEstado(EstadoIngreso.ANULADO);
        ingresoRepository.save(ingreso.get());
    }

    @Override
    public Ingreso createIngreso(IngresoDTO ingresoDTO) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(ingresoDTO.getMascotaId());

        //Validaciones
        if (!mascotaOptional.isPresent()) {
            throw new VeterinariaException("Mascota no encontrada");
        }if (!mascotaOptional.get().getActiva()) {
            throw new VeterinariaException("Mascota no activa, no se puede crear el ingreso");
        }if (!ingresoDTO.getDniPersonaIngreso().equalsIgnoreCase(mascotaOptional.get().getDniResponsable())){
            throw new VeterinariaException("Documento del responsable de la mascota no correponde con el documento proporcionado");
        }


        Ingreso ingreso = new Ingreso();
        ingreso.setMascota(mascotaOptional.get());
        ingreso.setDniPersonaRegistro(ingresoDTO.getDniPersonaIngreso());
        ingreso.setFechaAltaIngreso(ingresoDTO.getFechaAltaIngreso());
        ingreso.setEstado(EstadoIngreso.ALTA);
        return ingresoRepository.save(ingreso);

    }
}
