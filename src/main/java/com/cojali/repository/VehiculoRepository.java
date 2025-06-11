package com.cojali.repository;

import com.cojali.entity.Vehiculo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
    List<Vehiculo> findByClienteId(Integer clienteId);
}
