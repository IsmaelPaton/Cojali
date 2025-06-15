package com.cojali.controller;

import com.cojali.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cojali.entity.Empleado;


import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmpleadoService empleadoService;

    // Mostrar formulario para crear nuevo empleado
    @GetMapping("/nuevo-empleado")
    public String mostrarFormularioNuevoEmpleado(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "admin/nuevo-empleado";
    }

    @PostMapping("/guardar-empleado")
    public String guardarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
    	if (empleado.getRol() == Empleado.Rol.admin) {
    	    return "redirect:/admin/nuevo-empleado?error=rol_no_permitido";

        }
        empleadoService.guardar(empleado); 
        return "redirect:/admin/lista-empleados";
    }


    // Mostrar lista de empleados
    @GetMapping("/lista-empleados")
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.obtenerTodos();
        model.addAttribute("empleados", empleados);
        return "admin/lista-empleados";
    }
    
    @GetMapping("/editar-empleado/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Integer id, Model model) {
        Empleado empleado = empleadoService.buscarPorId(id);
        if (empleado == null) {
            return "redirect:/admin/lista-empleados?error=empleado_no_encontrado";
        }
        model.addAttribute("empleado", empleado);
        return "admin/editar-empleado";
    }
    
    @PostMapping("/actualizar-empleado")
    public String actualizarEmpleado(@ModelAttribute("empleado") Empleado empleado) {
        if (empleado.getRol() == Empleado.Rol.admin) {
            return "redirect:/admin/lista-empleados?error=rol_no_permitido";
        }
        empleadoService.guardar(empleado);
        return "redirect:/admin/lista-empleados";
    }
    @GetMapping("/eliminar-empleado/{id}")
    public String eliminarEmpleado(@PathVariable("id") Integer id) {
        empleadoService.eliminarPorId(id);
        return "redirect:/admin/lista-empleados";
    }


}
