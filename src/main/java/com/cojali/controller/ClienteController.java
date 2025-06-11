package com.cojali.controller;

import com.cojali.entity.Cliente;
import com.cojali.service.ClienteService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // === ÁREA ADMIN ===

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.getAllClientes());
        return "cliente/listaClientes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/formCliente";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.saveCliente(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable int id) {
        clienteService.deleteCliente(id);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable int id, Model model) {
        Cliente cliente = clienteService.getClienteById(id);
        model.addAttribute("cliente", cliente);
        return "cliente/formCliente";
    }

    // === REGISTRO CLIENTES ===

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/registro"; 
    }

    @PostMapping("/registro")
    public String registrarClienteDesdeWeb(@ModelAttribute Cliente cliente, Model model) {
        Cliente existente = clienteService.buscarPorEmail(cliente.getEmail());

        if (existente != null) {
            model.addAttribute("error", "Ya existe una cuenta con ese correo.");
            return "/registro";
        }

        clienteService.saveCliente(cliente);
        return "redirect:/login";
    }
    
    @GetMapping("/perfil")
    public String verPerfil(HttpSession session, Model model) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogueado");
        if (cliente == null) return "redirect:/login";
        model.addAttribute("cliente", cliente);
        return "cliente/perfil";
    }

    @PostMapping("/actualizar")
    public String actualizarPerfil(@ModelAttribute Cliente clienteActualizado, HttpSession session) {
        Cliente original = clienteService.obtenerPorId(clienteActualizado.getId());
        if (original != null) {
            original.setNombre(clienteActualizado.getNombre());
            original.setApellido(clienteActualizado.getApellido());
            original.setTelefono(clienteActualizado.getTelefono());
            original.setContraseña(clienteActualizado.getContraseña());
            clienteService.guardarCliente(original);
            session.setAttribute("clienteLogueado", original);
        }
        return "redirect:/inicio-cliente";
    }

}
