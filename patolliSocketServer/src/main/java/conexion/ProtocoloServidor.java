/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import Entidades.CondicionesPartida;
import mensajes.SolicitudALobby;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import logica.Lobby;
import mensajes.Mensaje;
import mensajes.MensajeALobby;
import mensajes.MensajeMovimiento;
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

    public void unirClienteALobby(SolicitudALobby solicitud, ClienteConectado cliente) throws IOException {
        Lobby lobby = manejadorClientes.comprobarSiLobbyExiste(solicitud.getCodigo());
        if (lobby != null) {
            if (lobby.lobbyLleno()) {
                if (manejadorClientes.comprobarUsuarioEnLobby(lobby, solicitud.getNombre())) {
                    cliente.getOut().writeObject(new Mensaje("UsuarioValido", null));
                    cliente.getOut().flush();
                    lobby.informarDeNuevoUsuario(solicitud.getNombre());
                    lobby.anadirJugadorAlLobby(cliente);
                    cliente.getOut().writeObject(new Mensaje("Codigo", solicitud.getCodigo()));
                    cliente.getOut().flush();

                    cliente.getOut().writeObject(new Mensaje("UsuariosConectados", lobby.obtenerNombresDeUsuarios()));
                    cliente.getOut().flush();

                } else {
                    cliente.getOut().writeObject(new Mensaje("UsuarioInvalido", "Ese usuario ya existe"));
                    cliente.getOut().flush();
                }
            } else {
                cliente.getOut().writeObject(new Mensaje("LobbyLleno", "Este lobby ya esta lleno"));
                cliente.getOut().flush();
            }
        } else {
            cliente.getOut().writeObject(new Mensaje("LobbyInexistente", "No existe ese lobby"));
            cliente.getOut().flush();
        }
    }

    public String crearLobby(CondicionesPartida condiciones) {
        String codigo = generarCodigo();
        Lobby lobby = new Lobby(codigo);
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

    public void manejarMensajesALobby(MensajeALobby mensaje) throws IOException {
        String codigo = mensaje.getCodigo();
        Lobby lobby = manejadorClientes.comprobarSiLobbyExiste(codigo);
        if (mensaje.getMensaje().equalsIgnoreCase("UsuarioListo")) {
            if (lobby.usuarioListo()) {
                lobby.informarATodosLosJugadoresEnLobby(new Mensaje("PartidaIniciada", null));
                for (int i = 0; i < lobby.getClientesEnLobby().size(); i++) {
                    lobby.getClientesEnLobby().get(i).getOut().writeObject(new Mensaje("TuNumeroDeJugador", i));
                    lobby.getClientesEnLobby().get(i).getOut().flush();
                }
            }
        } else if (mensaje.getMensaje().equalsIgnoreCase("Tirar")) {
            MensajeMovimiento movimiento = (MensajeMovimiento) mensaje.getContenido();
            for (int i = 0; i < lobby.getClientesEnLobby().size(); i++) {
                if (i == movimiento.getUsuarioTira()) {
                    continue;
                } else {
                    lobby.getClientesEnLobby().get(i).getOut().writeObject(new Mensaje("TiroJugador", movimiento));
                    lobby.getClientesEnLobby().get(i).getOut().flush();
                }
            }
            lobby.cambiarTurno();
            lobby.getClientesEnLobby().get(lobby.getUsuarioEnTurno()).getOut().writeObject(new Mensaje("Tu turno", null));
            lobby.getClientesEnLobby().get(lobby.getUsuarioEnTurno()).getOut().flush();

        } else if (mensaje.getMensaje().equalsIgnoreCase("Gano")) {
            MensajeMovimiento jugador = (MensajeMovimiento) mensaje.getContenido();
            lobby.getClientesEnLobby().get(jugador.getUsuarioTira()).getOut().writeObject(new Mensaje("PantallaVictoria", null));
            lobby.getClientesEnLobby().get(jugador.getUsuarioTira()).getOut().flush();
            
            for (int i = 0; i < lobby.getClientesEnLobby().size(); i++) {
                if (i != jugador.getUsuarioTira()) {
                    lobby.getClientesEnLobby().get(i).getOut().writeObject(new Mensaje("PantallaPerdida", null));
                    lobby.getClientesEnLobby().get(i).getOut().flush();
                }
            }
        }
    }
}
