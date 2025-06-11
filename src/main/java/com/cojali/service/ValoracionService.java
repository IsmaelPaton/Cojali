package com.cojali.service;

import com.cojali.entity.Valoracion;
import java.util.List;

public interface ValoracionService {
    Valoracion guardarValoracion(Valoracion valoracion);
    List<Valoracion> obtenerTodas();
    List<Valoracion> obtenerUltimasCinco();
    Double obtenerMediaPuntuacion();


}
