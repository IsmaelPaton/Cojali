package com.cojali.controller;

import com.cojali.entity.Empleado;
import com.cojali.entity.Empleado.Rol;
import com.cojali.service.EmpleadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "empleados/login-empleado";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String contraseña,
                                HttpSession session,
                                Model model) {
        Empleado empleado = empleadoService.login(email, contraseña);
        if (empleado != null) {
            session.setAttribute("empleadoLogueado", empleado);

            switch (empleado.getRol()) {
                case admin:
                    return "redirect:/empleados/admin/inicio";
                case recepcionista:
                    return "redirect:/empleados/recepcion/inicio";
                case mecanico:
                    return "redirect:/empleados/mecanico/inicio";
            }
        }

        model.addAttribute("error", true);
        return "empleados/login-empleado";
    }

    @GetMapping("/admin/inicio")
    public String inicioAdmin(HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoLogueado");
        if (empleado == null || empleado.getRol() != Rol.admin) {
            return "redirect:/empleados/login";
        }
        return "admin/admin-inicio";
    }

    @GetMapping("/recepcion/inicio")
    public String inicioRecepcion(HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoLogueado");
        if (empleado == null || empleado.getRol() != Rol.recepcionista) {
            return "redirect:/empleados/login";
        }
        return "recepcion/recepcion-inicio";
    }

    @GetMapping("/mecanico/inicio")
    public String inicioMecanico(HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoLogueado");
        if (empleado == null || empleado.getRol() != Rol.mecanico) {
            return "redirect:/empleados/login";
        }
        return "mecanico/mecanico-inicio";
    }
    
    @GetMapping("/admin/empleados")
    public String verListaEmpleados(Model model, HttpSession session) {
        Empleado admin = (Empleado) session.getAttribute("empleadoLogueado");
        if (admin == null || admin.getRol() != Rol.admin) {
            return "redirect:/empleados/login";
        }

        model.addAttribute("empleados", empleadoService.obtenerTodos());
        return "admin/lista-empleados";
    }
    @GetMapping("/admin/nuevo")
    public String mostrarFormularioNuevoEmpleado(Model model, HttpSession session) {
        Empleado admin = (Empleado) session.getAttribute("empleadoLogueado");
        if (admin == null || admin.getRol() != Rol.admin) {
            return "redirect:/empleados/login";
        }

        model.addAttribute("empleado", new Empleado());
        return "admin/nuevo-empleado";
    }

    @PostMapping("/admin/nuevo")
    public String guardarNuevoEmpleado(@ModelAttribute Empleado empleado, HttpSession session) {
        Empleado admin = (Empleado) session.getAttribute("empleadoLogueado");
        if (admin == null || admin.getRol() != Rol.admin) {
            return "redirect:/empleados/login";
        }

        empleadoService.guardar(empleado);
        return "redirect:/empleados/admin/empleados";
    }

    @GetMapping("/admin/editar/{id}")
    public String mostrarFormularioEditarEmpleado(@PathVariable Integer id, Model model, HttpSession session) {
        Empleado admin = (Empleado) session.getAttribute("empleadoLogueado");
        if (admin == null || admin.getRol() != Rol.admin) {
            return "redirect:/empleados/login";
        }

        Empleado empleado = empleadoService.buscarPorId(id);
        model.addAttribute("empleado", empleado);
        return "admin/editar-empleado";
    }

    @PostMapping("/admin/editar/{id}")
    public String actualizarEmpleado(@PathVariable Integer id, @ModelAttribute Empleado empleadoActualizado, HttpSession session) {
        Empleado admin = (Empleado) session.getAttribute("empleadoLogueado");
        if (admin == null || admin.getRol() != Rol.admin) {
            return "redirect:/empleados/login";
        }

        Empleado existente = empleadoService.buscarPorId(id);
        existente.setNombre(empleadoActualizado.getNombre());
        existente.setEmail(empleadoActualizado.getEmail());
        existente.setContraseña(empleadoActualizado.getContraseña());
        existente.setRol(empleadoActualizado.getRol());

        empleadoService.guardar(existente);

        return "redirect:/empleados/admin/empleados";
    }

    @GetMapping("/admin/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Integer id, HttpSession session) {
        Empleado admin = (Empleado) session.getAttribute("empleadoLogueado");
        if (admin == null || admin.getRol() != Rol.admin) {
            return "redirect:/empleados/login";
        }

        empleadoService.eliminarPorId(id);
        return "redirect:/empleados/admin/empleados";
    }
    

}
