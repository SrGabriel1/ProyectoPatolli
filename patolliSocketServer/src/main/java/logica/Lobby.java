/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import Entidades.CondicionesPartida;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import mensajes.Mensaje;
import modelo.ClienteConectado;

/**
 *
 * @author USER
 */
public class Lobby {
    private String codigo;
    private List<ClienteConectado> clientesEnLobby;
    private CondicionesPartida condiciones;
    

    public Lobby(String codigo) {
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
    
    public List<String> obtenerNombresDeUsuarios(){
        List<String> usuarios=new ArrayList<>();
        for(ClienteConectado c: clientesEnLobby){
            usuarios.add(c.getNombre());
        }
        return usuarios;
    }
    
    public void informarDeNuevoUsuario(String nombre) throws IOException{
        for(ClienteConectado c: clientesEnLobby){
            c.getOut().writeObject(new Mensaje("UsuarioNuevo",nombre));
            c.getOut().flush();
        }
    }
    
    public boolean lobbyLleno(){
        if(clientesEnLobby.size()==condiciones.getJugadores()){
            return false;
        }else{
            return true;
        }
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

    public CondicionesPartida getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(CondicionesPartida condiciones) {
        this.condiciones = condiciones;
    }

    @Override
    public String toString() {
        return "Lobby{" + "codigo=" + codigo + ", clientesEnLobby=" + clientesEnLobby + ", condiciones=" + condiciones + '}';
    }
    
    
    
    
}
