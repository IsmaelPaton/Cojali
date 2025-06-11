package com.cojali.service.impl;

import com.cojali.entity.Vehiculo;
import com.cojali.repository.VehiculoRepository;
import com.cojali.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public Vehiculo obtenerPorId(Integer id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Vehiculo> obtenerPorClienteId(Integer clienteId) {
        return vehiculoRepository.findByClienteId(clienteId);
    }

    @Override
    public void eliminarVehiculo(Integer id) {
        vehiculoRepository.deleteById(id);
    }

    @Override
    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

    @Override
    public boolean eliminarPorId(Integer id) {
        try {
            Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);
            if (vehiculo == null) {
                return false;
            }

            vehiculoRepository.deleteById(id);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

}
