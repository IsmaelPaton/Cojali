package com.cojali.service;

import com.cojali.entity.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaService {
    Reserva guardarReserva(Reserva reserva);
    boolean existeReservaEnFechaYHora(LocalDate fecha, LocalTime hora);
    List<Reserva> obtenerTodas();
    Reserva getReservaById(int id);
    void eliminarReserva(int id);
    Reserva obtenerPorId(Integer id);
    void modificarReserva(Integer id, Reserva modificada);
    void eliminar(Integer id);
    void guardar(Reserva reserva);
    List<Reserva> obtenerActivas();



}
