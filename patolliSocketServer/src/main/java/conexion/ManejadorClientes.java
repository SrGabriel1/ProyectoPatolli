/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import logica.Lobby;
import modelo.ClienteConectado;

/**
 *
 * @author USER
 */
public class ManejadorClientes {
    private List<HiloCliente> clientes;
    private List<Lobby> lobbys;

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
    
    public boolean anadirClienteALobby(String codigo, HiloCliente cliente){
        for(Lobby l:lobbys){
            if(l.getCodigo().equalsIgnoreCase(codigo)){
                if (l.comprobarJugadorConMismoNombre(codigo)) {
                    return false;
                } else {
                    l.anadirJugadorAlLobby(new ClienteConectado(cliente.getClienteSocket(),));
                }
            }
        }
        return true;
    }
}
