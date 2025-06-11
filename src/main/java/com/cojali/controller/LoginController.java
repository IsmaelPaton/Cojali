package com.cojali.controller;

import com.cojali.entity.Cliente;
import com.cojali.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                HttpSession session,
                                Model model) {

        Cliente cliente = clienteService.buscarPorEmail(email);

        if (cliente != null && cliente.getContraseña().equals(password)) {
            session.setAttribute("clienteLogueado", cliente); // guardar el cliente en sesión
            return "redirect:/inicio-cliente";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }
    }
    
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Cierra la sesión
        return "redirect:/"; // Redirige al index
    }


}
