package com.cojali.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(String nombre, String email, String telefono, String mensaje) {
        SimpleMailMessage correo = new SimpleMailMessage();
        correo.setTo("tallercojali@gmail.com");
        correo.setSubject("Nuevo mensaje de contacto desde la web");
        correo.setText(
                "Nombre y apellidos: " + nombre + "\n" +
                "Correo electrónico: " + email + "\n" +
                "Teléfono: " + telefono + "\n" +
                "Descripción: " + mensaje
        );

        mailSender.send(correo);
    }
}
