package com.cojali.controller;

import com.cojali.entity.Cliente;
import com.cojali.entity.Reserva;
import com.cojali.service.ReservaService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/nueva")
    public String mostrarFormularioReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "cliente/reserva";
    }

    @PostMapping("/guardar")
    public String guardarReserva(@ModelAttribute("reserva") Reserva reserva,
                                  HttpSession session,
                                  Model model) {

        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

        if (cliente == null) {
            return "redirect:/login";
        }

        reserva.setCliente(cliente);

        if (reservaService.existeReservaEnFechaYHora(reserva.getFecha(), reserva.getHora())) {
            model.addAttribute("error", "Ya existe una reserva en ese horario.");
            return "cliente/reserva";
        }

        reservaService.guardarReserva(reserva);
        model.addAttribute("mensaje", "Reserva realizada correctamente.");
        return "cliente/reserva";
    }
    
    @GetMapping("/mis-reservas")
    public String mostrarReservasCliente(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

        if (cliente == null) {
            return "redirect:/login";
        }

        List<Reserva> reservasCliente = reservaService.obtenerTodas()
                .stream()
                .filter(r -> r.getCliente().getId() == cliente.getId())
                .toList();

        model.addAttribute("reservas", reservasCliente);
        return "cliente/misReservas";
    }
    
    @GetMapping("/cancelar/{id}")
    public String cancelarReserva(@PathVariable int id, HttpSession session, RedirectAttributes redirectAttributes) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

        if (cliente == null) {
            return "redirect:/login";
        }

        Reserva reserva = reservaService.getReservaById(id);

        if (reserva != null && reserva.getCliente().getId() == cliente.getId()) {
            reservaService.eliminarReserva(id);
            redirectAttributes.addFlashAttribute("mensaje", "Reserva cancelada correctamente.");
        }

        return "redirect:/reservas/mis-reservas";
    }

    @GetMapping("/api/reservas")
    @ResponseBody
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaService.obtenerTodas();
    }

    @GetMapping("/modificar/{id}")
    public String mostrarFormularioModificacion(@PathVariable Integer id, Model model) {
        Reserva reserva = reservaService.obtenerPorId(id);
        model.addAttribute("reserva", reserva);
        return "reservas/modificar";
    }

    @PostMapping("/modificar/{id}")
    public String procesarModificacion(@PathVariable Integer id, @ModelAttribute Reserva reservaModificada, RedirectAttributes redirectAttributes) {
        reservaService.modificarReserva(id, reservaModificada);
        redirectAttributes.addFlashAttribute("mensaje", "Reserva modificada correctamente");
        return "redirect:/reservas/mis-reservas";
    }



}
