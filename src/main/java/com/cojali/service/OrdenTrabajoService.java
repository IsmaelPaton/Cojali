package com.cojali.service;

import com.cojali.entity.OrdenTrabajo;
import java.util.List;

public interface OrdenTrabajoService {
    List<OrdenTrabajo> obtenerTodas();
    void eliminarPorId(Integer id);
    
}
