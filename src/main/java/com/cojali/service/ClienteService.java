package com.cojali.service;

import com.cojali.entity.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> getAllClientes();
    Cliente getClienteById(int id);
    Cliente saveCliente(Cliente cliente);
    void deleteCliente(int id);
    Cliente buscarPorEmail(String email);
    Cliente obtenerPorId(int id);
    Cliente guardarCliente(Cliente cliente);
    Cliente obtenerPorEmail(String email);
    void guardar(Cliente cliente);
    List<Cliente> obtenerTodos();
    Cliente obtenerPorId(Integer id);
    void eliminar(Integer id);
    boolean eliminarPorId(Integer id);



   

}
