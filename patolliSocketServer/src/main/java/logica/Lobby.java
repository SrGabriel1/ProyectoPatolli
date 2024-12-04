/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import Entidades.CondicionesPartida;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    private int usuariosListos;
    private int usuarioEnTurno;

    public Lobby(String codigo) {
        this.codigo = codigo;
        this.clientesEnLobby = new ArrayList<>();
        usuarioEnTurno=0;
    }

    public void anadirJugadorAlLobby(ClienteConectado cliente) {
        clientesEnLobby.add(cliente);
    }
    
    public void cambiarTurno(){
        usuarioEnTurno++;
        if(usuarioEnTurno==clientesEnLobby.size()){
            usuarioEnTurno=0;
        }
    }

    public boolean comprobarJugadorConMismoNombre(String nombre) {
        ClienteConectado cli = clientesEnLobby.stream().filter(c -> c.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
        return cli != null;
    }

    public List<String> obtenerNombresDeUsuarios() {
        return clientesEnLobby.stream()
                .map(ClienteConectado::getNombre)
                .collect(Collectors.toList());
    }

    public void informarDeNuevoUsuario(String nombre) throws IOException {
        for (ClienteConectado c : clientesEnLobby) {
            c.getOut().writeObject(new Mensaje("UsuarioNuevo", nombre));
            c.getOut().flush();
        }
    }

    public boolean lobbyLleno() {
        if (clientesEnLobby.size() == condiciones.getJugadores()) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean usuarioListo(){
        usuariosListos++;
        if(usuariosListos==clientesEnLobby.size() && usuariosListos>1){
            return true;
        }else{
            return false;
        }
    }
    
    public void informarATodosLosJugadoresEnLobby(Mensaje mensaje) throws IOException{
        for(ClienteConectado c:clientesEnLobby){
            c.getOut().writeObject(mensaje);
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

    public int getUsuarioEnTurno() {
        return usuarioEnTurno;
    }
    
    

    @Override
    public String toString() {
        return "Lobby{" + "codigo=" + codigo + ", clientesEnLobby=" + clientesEnLobby + ", condiciones=" + condiciones + '}';
    }

}
