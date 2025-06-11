package com.cojali.controller;

import com.cojali.entity.Cliente;
import com.cojali.entity.Empleado;
import com.cojali.entity.Reserva;
import com.cojali.entity.Vehiculo;
import com.cojali.service.ClienteService;
import com.cojali.service.ReservaService;
import com.cojali.service.VehiculoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cojali.service.OrdenTrabajoService;
import com.cojali.entity.OrdenTrabajo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/recepcion")
public class RecepcionController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VehiculoService vehiculoService;
    
    @Autowired
    private OrdenTrabajoService ordenTrabajoService;


    @GetMapping("/inicio")
    public String mostrarReservas(Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoLogueado");
        if (empleado == null || empleado.getRol() != Empleado.Rol.recepcionista) {
            return "redirect:/empleados/login";
        }

        List<Reserva> reservas = reservaService.obtenerActivas();
        model.addAttribute("reservas", reservas);
        return "recepcion/recepcion-inicio";
    }

    @PostMapping("/cancelar-reserva")
    public String cancelarReserva(@RequestParam("id") Integer id) {
        reservaService.eliminar(id);
        return "redirect:/recepcion/inicio";
    }

    @GetMapping("/registrar-cliente")
    public String mostrarFormularioRegistroCliente(HttpSession session, Model model) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoLogueado");
        if (empleado == null || empleado.getRol() != Empleado.Rol.recepcionista) {
            return "redirect:/empleados/login";
        }
        model.addAttribute("cliente", new Cliente());
        return "recepcion/registrar-cliente";
    }

    @PostMapping("/guardar-cliente")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        Cliente nuevo = clienteService.guardarCliente(cliente);
        return "redirect:/recepcion/registrar-vehiculo?clienteId=" + nuevo.getId();
    }

    @GetMapping("/registrar-vehiculo")
    public String mostrarFormularioVehiculo(@RequestParam("clienteId") Integer clienteId, Model model) {
        model.addAttribute("clienteId", clienteId);
        return "recepcion/registrar-vehiculo";
    }

    @PostMapping("/guardar-vehiculo")
    public String guardarVehiculo(@RequestParam("clienteId") Integer clienteId,
                                  @RequestParam("marca") String marca,
                                  @RequestParam("modelo") String modelo,
                                  @RequestParam("matricula") String matricula,
                                  @RequestParam("anio") Integer anio) {

        Cliente cliente = clienteService.obtenerPorId(clienteId);
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setCliente(cliente);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setMatricula(matricula);
        vehiculo.setAnio(anio);

        vehiculoService.guardarVehiculo(vehiculo);
        return "redirect:/recepcion/inicio";
    }

    @GetMapping("/editar-vehiculo/{id}")
    public String mostrarFormularioEditarVehiculo(@PathVariable Integer id, Model model) {
        Vehiculo vehiculo = vehiculoService.obtenerPorId(id);
        model.addAttribute("vehiculo", vehiculo);
        return "recepcion/editar-vehiculo";
    }

    @PostMapping("/actualizar-vehiculo")
    public String actualizarVehiculo(@ModelAttribute Vehiculo vehiculo) {
        vehiculoService.guardarVehiculo(vehiculo);
        return "redirect:/recepcion/vehiculos";
    }

    @PostMapping("/eliminar-vehiculo")
    public String eliminarVehiculo(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean eliminado = vehiculoService.eliminarPorId(id);
        if (eliminado) {
            redirectAttributes.addFlashAttribute("exito", "✅ Vehículo eliminado correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ No se puede eliminar el vehículo porque está relacionado con órdenes de trabajo.");
        }
        return "redirect:/recepcion/vehiculos";
    }



    @GetMapping("/crear-reserva")
    public String mostrarFormularioReserva(Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoLogueado");
        if (empleado == null || empleado.getRol() != Empleado.Rol.recepcionista) {
            return "redirect:/empleados/login";
        }

        model.addAttribute("clientes", clienteService.obtenerTodos());
        return "recepcion/crear-reserva";
    }

    @PostMapping("/guardar-reserva")
    public String guardarReserva(@RequestParam("clienteId") Integer clienteId,
                                 @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                 @RequestParam("hora") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime hora,
                                 @RequestParam("servicio") String servicio,
                                 RedirectAttributes redirectAttributes) {

        DayOfWeek dia = fecha.getDayOfWeek();
        if (dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY) {
            redirectAttributes.addFlashAttribute("error", "❌ El taller está cerrado los sábados y domingos.");
            return "redirect:/recepcion/crear-reserva";
        }

        Cliente cliente = clienteService.obtenerPorId(clienteId);
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setFecha(fecha);
        reserva.setHora(hora);
        reserva.setServicio(servicio);
        reserva.setEstado("Activa");

        reservaService.guardar(reserva);
        redirectAttributes.addFlashAttribute("exito", "✅ Reserva creada correctamente.");
        return "redirect:/recepcion/inicio";
    }

    @PostMapping("/completar-reserva")
    public String completarReserva(@RequestParam("id") Integer id) {
        Reserva reserva = reservaService.obtenerPorId(id);
        if (reserva != null) {
            reserva.setEstado("Completada");
            reservaService.guardar(reserva);
        }
        return "redirect:/recepcion/inicio";
    }

    @GetMapping("/horas-ocupadas")
    @ResponseBody
    public List<LocalTime> obtenerHorasOcupadas(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Reserva> reservasDelDia = reservaService.obtenerTodas();
        return reservasDelDia.stream()
                .filter(r -> r.getFecha().equals(fecha))
                .map(Reserva::getHora)
                .toList();
    }

    @GetMapping("/clientes")
    public String verTodosLosClientes(Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoLogueado");
        if (empleado == null || empleado.getRol() != Empleado.Rol.recepcionista) {
            return "redirect:/empleados/login";
        }

        List<Cliente> clientes = clienteService.obtenerTodos();
        model.addAttribute("clientes", clientes);
        return "recepcion/clientes-lista";
    }

    @GetMapping("/editar-cliente/{id}")
    public String editarCliente(@PathVariable Integer id, Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoLogueado");
        if (empleado == null || empleado.getRol() != Empleado.Rol.recepcionista) {
            return "redirect:/empleados/login";
        }

        Cliente cliente = clienteService.obtenerPorId(id);
        model.addAttribute("cliente", cliente);
        return "recepcion/editar-cliente";
    }

    @PostMapping("/actualizar-cliente")
    public String actualizarCliente(@ModelAttribute Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/recepcion/clientes";
    }

    @PostMapping("/eliminar-cliente")
    public String eliminarCliente(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean eliminado = clienteService.eliminarPorId(id);
        if (!eliminado) {
            redirectAttributes.addFlashAttribute("noSePuedeEliminar", true);
        } else {
            redirectAttributes.addFlashAttribute("exito", "✅ Cliente eliminado correctamente.");
        }
        return "redirect:/recepcion/clientes";
    }

    @GetMapping("/vehiculos")
    public String mostrarVehiculos(Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoLogueado");
        if (empleado == null || empleado.getRol() != Empleado.Rol.recepcionista) {
            return "redirect:/empleados/login";
        }

        model.addAttribute("vehiculos", vehiculoService.obtenerTodos());
        return "recepcion/vehiculos-lista";
    }
    
 // Mostrar todas las órdenes de trabajo
    @GetMapping("/ordenes")
    public String mostrarOrdenes(Model model, HttpSession session) {
        Empleado empleado = (Empleado) session.getAttribute("empleadoLogueado");
        if (empleado == null || empleado.getRol() != Empleado.Rol.recepcionista) {
            return "redirect:/empleados/login";
        }

        List<OrdenTrabajo> ordenes = ordenTrabajoService.obtenerTodas();
        model.addAttribute("ordenes", ordenes);
        return "recepcion/ordenes-lista";
    }

    // Eliminar una orden de trabajo
    @PostMapping("/eliminar-orden")
    public String eliminarOrden(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {
        ordenTrabajoService.eliminarPorId(id);
        redirectAttributes.addFlashAttribute("exito", "✅ Orden de trabajo eliminada.");
        return "redirect:/recepcion/ordenes";
    }

} 
