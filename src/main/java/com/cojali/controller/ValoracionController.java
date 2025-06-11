package com.cojali.controller;

import com.cojali.entity.Cliente;
import com.cojali.entity.Valoracion;
import com.cojali.service.ValoracionService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.internet.MimeMessage;

@Controller
@RequestMapping("/valoraciones")
public class ValoracionController {

    @Autowired
    private ValoracionService valoracionService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String correoRemitente;

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("valoracion", new Valoracion());
        return "cliente/valorar";
    }

    @PostMapping("/guardar")
    public String procesarFormulario(@ModelAttribute("valoracion") Valoracion valoracion,
                                     HttpSession session,
                                     Model model) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");

        if (cliente == null) {
            return "redirect:/login";
        }

        valoracion.setCliente(cliente);
        valoracionService.guardarValoracion(valoracion);

        try {
            enviarCorreoAlTaller(cliente, valoracion);
        } catch (MessagingException e) {
            model.addAttribute("error", "Valoración guardada, pero hubo un problema al enviar el correo.");
            return "cliente/valorar";
        }

        model.addAttribute("mensaje", "¡Gracias por tu valoración!");
        return "cliente/valorar";
    }

    private void enviarCorreoAlTaller(Cliente cliente, Valoracion valoracion) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setFrom(correoRemitente);
        helper.setTo("tallercojali@gmail.com"); 
        helper.setSubject("Nueva Valoración de Cliente");

        String cuerpo = """
            <h2>¡Nueva valoración recibida!</h2>
            <p><strong>Cliente:</strong> %s %s</p>
            <p><strong>Email:</strong> %s</p>
            <p><strong>Puntuación:</strong> %d estrellas</p>
            <p><strong>Mensaje:</strong> %s</p>
            """.formatted(
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getEmail(),
                valoracion.getPuntuacion(),
                valoracion.getMensaje() != null ? valoracion.getMensaje() : "Sin comentarios"
        );

        helper.setText(cuerpo, true);
        mailSender.send(mensaje);
    }
}
