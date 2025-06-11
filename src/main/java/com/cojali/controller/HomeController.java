package com.cojali.controller;

import com.cojali.entity.Cliente;
import com.cojali.service.ValoracionService;

import jakarta.servlet.http.HttpSession;
import com.cojali.entity.Valoracion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/inicio-cliente")
    public String inicioCliente(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

        if (cliente == null) {
            return "redirect:/login";
        }

        model.addAttribute("cliente", cliente);
        return "cliente"; 
    }
    
    @Autowired
    private ValoracionService valoracionService;

    @GetMapping("/")
    public String index(Model model) {
        List<Valoracion> ultimasValoraciones = valoracionService.obtenerUltimasCinco();
        Double mediaPuntuacion = valoracionService.obtenerMediaPuntuacion();

        model.addAttribute("ultimasValoraciones", ultimasValoraciones);
        model.addAttribute("mediaPuntuacion", mediaPuntuacion);
        return "index";
    }


}
