package com.cojali.repository;

import com.cojali.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // Para comprobar si ya existe una reserva en una fecha y hora concreta
    Optional<Reserva> findByFechaAndHora(LocalDate fecha, LocalTime hora);
    List<Reserva> findByEstado(String estado);

}
