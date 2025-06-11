package com.cojali.service.impl;

import com.cojali.entity.Factura;
import com.cojali.entity.Cliente;
import com.cojali.repository.FacturaRepository;
import com.cojali.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public Factura guardarFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    public List<Factura> obtenerFacturasPorCliente(Cliente cliente) {
        return facturaRepository.findByCliente(cliente);
    }

    @Override
    public List<Factura> obtenerTodas() {
        return facturaRepository.findAll();
    }
}
