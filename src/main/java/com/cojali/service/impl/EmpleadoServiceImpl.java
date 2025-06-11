package com.cojali.service.impl;

import com.cojali.entity.Empleado;
import com.cojali.repository.EmpleadoRepository;
import com.cojali.service.EmpleadoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado login(String email, String contraseña) {
        Empleado empleado = empleadoRepository.findByEmail(email);
        if (empleado != null && empleado.getContraseña().trim().equals(contraseña.trim())) {
            return empleado;
        }
        return null;
 }
    
    @Override
    public List<Empleado> obtenerTodos() {
        return empleadoRepository.findAll();
    }

    @Override
    public void guardar(Empleado empleado) {
        empleadoRepository.save(empleado);
    }

    @Override
    public Empleado buscarPorId(Integer id) {
        return empleadoRepository.findById(id).orElse(null);
    }
    @Override
    public void eliminarPorId(Integer id) {
        empleadoRepository.deleteById(id);
    }

}
