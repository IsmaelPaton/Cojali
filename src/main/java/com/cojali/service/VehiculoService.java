package com.cojali.service;

import com.cojali.entity.Vehiculo;
import java.util.List;

public interface VehiculoService {
    Vehiculo guardarVehiculo(Vehiculo vehiculo);
    Vehiculo obtenerPorId(Integer id);
    List<Vehiculo> obtenerPorClienteId(Integer clienteId);
    void eliminarVehiculo(Integer id);
    List<Vehiculo> obtenerTodos(); 
    boolean eliminarPorId(Integer id);


}
