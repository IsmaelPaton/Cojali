package com.cojali.service.impl;

import com.cojali.entity.OrdenTrabajo;
import com.cojali.repository.OrdenTrabajoRepository;
import com.cojali.service.OrdenTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenTrabajoServiceImpl implements OrdenTrabajoService {

    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    @Override
    public List<OrdenTrabajo> obtenerTodas() {
        return ordenTrabajoRepository.findAll();
    }

    @Override
    public void eliminarPorId(Integer id) {
        ordenTrabajoRepository.deleteById(id);
    }
}
