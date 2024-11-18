/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import Entidades.CondicionesPartida;
import Entidades.SolicitudALobby;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import logica.Lobby;
import mensajes.Mensaje;
import modelo.ClienteConectado;

/**
 *
 * @author USER
 */
public class ProtocoloServidor {
    private ManejadorClientes manejadorClientes;

    public ProtocoloServidor(ManejadorClientes manejadorClientes) {
        this.manejadorClientes = manejadorClientes;
    }
    
    public void unirClienteALobby(SolicitudALobby solicitud,ClienteConectado cliente) throws IOException{
        Lobby lobby=manejadorClientes.comprobarSiLobbyExiste(solicitud.getCodigo());
        if(lobby!=null){
            if (lobby.lobbyLleno()) {
                if (manejadorClientes.comprobarUsuarioEnLobby(lobby, solicitud.getNombre())) {
                    cliente.getOut().writeObject(new Mensaje("UsuarioValido", null));
                    cliente.getOut().flush();
                    lobby.informarDeNuevoUsuario(solicitud.getNombre());
                    lobby.anadirJugadorAlLobby(cliente);
                    cliente.getOut().writeObject(new Mensaje("UsuariosConectados", lobby.obtenerNombresDeUsuarios()));
                    cliente.getOut().flush();

                } else {
                    cliente.getOut().writeObject(new Mensaje("UsuarioInvalido", "Ese usuario ya existe"));
                    cliente.getOut().flush();
                }
            }else{
                cliente.getOut().writeObject(new Mensaje("LobyLleno", "Este lobby ya esta lleno"));
                cliente.getOut().flush();
            }
        }else{
            cliente.getOut().writeObject(new Mensaje("LobbyInexistente", "No existe ese lobby"));
            cliente.getOut().flush();
        }
        System.out.println( lobby.getClientesEnLobby());
    }
    
    public String crearLobby(CondicionesPartida condiciones){
        String codigo=generarCodigo();
        Lobby lobby=new Lobby(codigo);
        lobby.setCondiciones(condiciones);
        manejadorClientes.anadirLobby(lobby);
        System.out.println(codigo);
        return codigo;
    }
    public String generarCodigo() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder(4);
        
        for (int i = 0; i < 4; i++) {
            char letra = (char) (random.nextInt(26) + 'A');
            codigo.append(letra);
        }
        
        return codigo.toString();
    }
}
