package com.cojali.repository;

import com.cojali.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Empleado findByEmailAndContraseña(String email, String contraseña);
    
    Empleado findByEmail(String email);

}

