/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import modelo.ClienteConectado;

/**
 *
 * @author USER
 */
public class ManejadorClientes {
    private List<HiloCliente> clientes;

    public ManejadorClientes() {
        this.clientes = new ArrayList<>();
    }

    public void agregarCliente(HiloCliente hiloCliente) {
        clientes.add(hiloCliente);
    }

    public void eliminarCliente(HiloCliente hiloCliente) {
        clientes.remove(hiloCliente);
    }

    public List<HiloCliente> obtenerClientes() {
        return clientes;
    }
}
