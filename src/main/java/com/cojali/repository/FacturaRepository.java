package com.cojali.repository;

import com.cojali.entity.Factura;
import com.cojali.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Integer> {

    List<Factura> findByCliente(Cliente cliente);
}
