package com.cojali.controller;


import com.cojali.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactoController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar-correo")
    public String enviarCorreo(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam String mensaje) {

        emailService.enviarCorreo(nombre, email, telefono, mensaje);
        return "redirect:/?enviado=true";
    }
}
