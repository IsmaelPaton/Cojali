package com.cojali.controller;

import com.cojali.entity.Cliente;
import com.cojali.entity.Factura;
import com.cojali.service.FacturaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/facturas")
    public String mostrarFacturasCliente(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

        if (cliente == null) {
            return "redirect:/login";
        }

        List<Factura> facturas = facturaService.obtenerFacturasPorCliente(cliente);
        model.addAttribute("facturas", facturas);
        return "cliente/facturas";
    }
}
