/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import mensajes.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class HiloCliente implements Runnable{
    Socket clienteSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ProtocoloServidor protocolo;
    private ManejadorClientes manejadorClientes;
    
    public HiloCliente(Socket clienteSocket, ManejadorClientes manejadorClientes) {
        this.manejadorClientes=manejadorClientes;
            protocolo=new ProtocoloServidor();
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
                        
                        
                    }else if(mensajeRecibido.getTipo().equalsIgnoreCase("UnirseLobby")){
                        protocolo.unirClienteALobby();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public Socket getClienteSocket() {
        return clienteSocket;
    }
    
}
