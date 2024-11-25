/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import mensajes.SolicitudALobby;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import logica.Lobby;
import mensajes.MensajeALobby;
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
        this.lobbys = new ArrayList<>();
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
    
    public void anadirLobby(Lobby lobby){
        lobbys.add(lobby);
        System.out.println("Lobby agregado exitosamente");
        System.out.println(lobby);
    }
    
    public Lobby comprobarSiLobbyExiste(String codigo){
        for(Lobby l: lobbys){
            if(l.getCodigo().equalsIgnoreCase(codigo)){
                return l;
            }
        }
        return null;
    }
    
    public boolean comprobarUsuarioEnLobby(Lobby lobby,String nombre){
        for( ClienteConectado c:lobby.getClientesEnLobby()){
            if(c.getNombre().equalsIgnoreCase(nombre)){
                return false;
            }
        }
        return true;
    }
    public void recibirMensajeDeLobby(MensajeALobby mensaje){
        
    }
}
