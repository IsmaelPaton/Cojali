package com.cojali.service.impl;

import com.cojali.entity.Cliente;
import com.cojali.entity.Reserva;
import com.cojali.repository.ClienteRepository;
import com.cojali.repository.ReservaRepository;
import com.cojali.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    @Autowired
    private ReservaRepository reservaRepository;


    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getClienteById(int id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void deleteCliente(int id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    // Métodos necesarios para zona cliente (alias de los ya existentes)
    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return saveCliente(cliente); 
    }

    @Override
    public Cliente obtenerPorId(int id) {
        return getClienteById(id); 
    }

    @Override
    public Cliente obtenerPorEmail(String email) {
        return buscarPorEmail(email); 
    }
    
    @Override
    public void guardar(Cliente cliente) {
        clienteRepository.save(cliente);
    }
    
    @Override
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente obtenerPorId(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }
    
    @Override
    public void eliminar(Integer id) {
        clienteRepository.deleteById(id);
    }
    @Override
    public boolean eliminarPorId(Integer id) {
        // Verifica si tiene reservas activas
        List<Reserva> reservas = reservaRepository.findAll()
            .stream()
            .filter(r -> r.getCliente().getId().equals(id))
            .filter(r -> !"Completada".equalsIgnoreCase(r.getEstado()))
            .toList();

        if (!reservas.isEmpty()) {
            return false; // No se puede eliminar
        }

        clienteRepository.deleteById(id);
        return true; // Eliminación exitosa
    }

}
