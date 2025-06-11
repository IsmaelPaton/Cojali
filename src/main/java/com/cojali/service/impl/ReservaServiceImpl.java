package com.cojali.service.impl;

import com.cojali.entity.Reserva;
import com.cojali.repository.ReservaRepository;
import com.cojali.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public boolean existeReservaEnFechaYHora(LocalDate fecha, LocalTime hora) {
        return reservaRepository.findByFechaAndHora(fecha, hora).isPresent();
    }

    @Override
    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }
    @Override
    public Reserva getReservaById(int id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarReserva(int id) {
        reservaRepository.deleteById(id);
    }
    
    @Override
    public Reserva obtenerPorId(Integer id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public void modificarReserva(Integer id, Reserva modificada) {
        Reserva original = reservaRepository.findById(id).orElse(null);
        if (original != null) {
            original.setFecha(modificada.getFecha());
            original.setHora(modificada.getHora());
            original.setServicio(modificada.getServicio());
            reservaRepository.save(original);
        }
    }
    

    @Override
    public void eliminar(Integer id) {
        reservaRepository.deleteById(id);
    }

    @Override
    public void guardar(Reserva reserva) {
        reservaRepository.save(reserva);
    }
    @Override
    public List<Reserva> obtenerActivas() {
        return reservaRepository.findByEstado("Activa");
    }

}
