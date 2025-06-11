package com.cojali.service;

import com.cojali.entity.Factura;
import com.cojali.entity.Cliente;

import java.util.List;

public interface FacturaService {

    Factura guardarFactura(Factura factura);

    List<Factura> obtenerFacturasPorCliente(Cliente cliente);

    List<Factura> obtenerTodas();
}
