/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.util.ArrayList;
import java.util.List;
import modelo.ClienteConectado;

/**
 *
 * @author USER
 */
public class Lobby {
    private String codigo;
    private List<ClienteConectado> clientesEnLobby;

    public Lobby(String codigo, List<ClienteConectado> clientesEnLobby) {
        this.codigo = codigo;
        this.clientesEnLobby = new ArrayList<>();
    }
    
    public void anadirJugadorAlLobby(ClienteConectado cliente){
        clientesEnLobby.add(cliente);
    }
    
    public boolean comprobarJugadorConMismoNombre(String nombre){
        for(ClienteConectado c:clientesEnLobby){
            if(c.getNombre().equalsIgnoreCase(nombre)){
                return true;
            }
        }
        return false;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<ClienteConectado> getClientesEnLobby() {
        return clientesEnLobby;
    }

    public void setClientesEnLobby(List<ClienteConectado> clientesEnLobby) {
        this.clientesEnLobby = clientesEnLobby;
    }
    
    
    
    
}
