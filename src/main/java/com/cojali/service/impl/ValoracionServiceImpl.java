package com.cojali.service.impl;

import com.cojali.entity.Valoracion;
import com.cojali.repository.ValoracionRepository;
import com.cojali.service.ValoracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValoracionServiceImpl implements ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Override
    public Valoracion guardarValoracion(Valoracion valoracion) {
        return valoracionRepository.save(valoracion);
    }

    @Override
    public List<Valoracion> obtenerTodas() {
        return valoracionRepository.findAll();
    }

    @Override
    public List<Valoracion> obtenerUltimasCinco() {
        return valoracionRepository.findTop5ByOrderByFechaDesc();
    }
    
    @Override
    public Double obtenerMediaPuntuacion() {
        List<Valoracion> valoraciones = valoracionRepository.findAll();
        if (valoraciones.isEmpty()) return 0.0;

        double suma = valoraciones.stream()
                                  .mapToInt(Valoracion::getPuntuacion)
                                  .sum();

        return Math.round((suma / valoraciones.size()) * 10.0) / 10.0; // redondeado a 1 decimal
    }


}
