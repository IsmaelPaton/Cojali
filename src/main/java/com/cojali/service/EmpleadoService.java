package com.cojali.service;

import java.util.List;

import com.cojali.entity.Empleado;

public interface EmpleadoService {
    Empleado login(String email, String contraseña);
    List<Empleado> obtenerTodos();
    void guardar(Empleado empleado);
    Empleado buscarPorId(Integer id);
    void eliminarPorId(Integer id);

    
    
}
