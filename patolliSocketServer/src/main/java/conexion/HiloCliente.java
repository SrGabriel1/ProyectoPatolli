/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import Entidades.CondicionesPartida;
import GUIs.vistaPartidaGanada;
import mensajes.SolicitudALobby;
import mensajes.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.MensajeALobby;
import modelo.ClienteConectado;

/**
 *
 * @author USER
 */
public class HiloCliente implements Runnable {

    Socket clienteSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ProtocoloServidor protocolo;
    private ManejadorClientes manejadorClientes;

    public HiloCliente(Socket clienteSocket, ManejadorClientes manejadorClientes) {
        this.manejadorClientes = manejadorClientes;
        this.protocolo = new ProtocoloServidor(manejadorClientes);
        try {
            this.clienteSocket = clienteSocket;
            out = new ObjectOutputStream(clienteSocket.getOutputStream());
            in = new ObjectInputStream(clienteSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Mensaje mensajeRecibido = (Mensaje) in.readObject();
                if (mensajeRecibido.getTipo().equals("CrearLobby")) {

                    CondicionesPartida condiciones = (CondicionesPartida) mensajeRecibido.getContenido();
                    String codigo = protocolo.crearLobby(condiciones);
                    out.writeObject(new Mensaje("Codigo", codigo));
                    out.flush();

                    String nombre = condiciones.getAdmin().getNombre();
                    SolicitudALobby solicitud = new SolicitudALobby(nombre, codigo);
                    ClienteConectado clienteConectado = new ClienteConectado(out, in, nombre);
                    protocolo.unirClienteALobby(solicitud, clienteConectado);

                } else if (mensajeRecibido.getTipo().equalsIgnoreCase("UnirseALobby")) {
                    SolicitudALobby solicitud = (SolicitudALobby) mensajeRecibido.getContenido();
                    ClienteConectado cliente = new ClienteConectado(out, in, solicitud.getNombre());
                    protocolo.unirClienteALobby(solicitud, cliente);
                } else if (mensajeRecibido.getTipo().equalsIgnoreCase("MensajeALobby")) {
                    MensajeALobby mensaje = (MensajeALobby) mensajeRecibido.getContenido();
                    protocolo.manejarMensajesALobby(mensaje);
                } else if (mensajeRecibido.getTipo().equalsIgnoreCase("PantallaVictoria")) {
                    vistaPartidaGanada pantalla = new vistaPartidaGanada();
                    pantalla.setVisible(true);
                }else if (mensajeRecibido.getTipo().equalsIgnoreCase("PantallaPerdida")) {
                    vistaPartidaGanada pantalla = new vistaPartidaGanada();
                    pantalla.setVisible(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Socket getClienteSocket() {
        return clienteSocket;
    }

    public void mandarMensajeAlCliente(Mensaje mensaje) {
        try {
            out.writeObject(mensaje);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
