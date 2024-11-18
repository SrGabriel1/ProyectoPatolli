/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import mensajes.Mensaje;

/**
 *
 * @author USER
 */
public class Cliente extends Observable implements Runnable {

    private int puerto = 4444;
    private String nombreJugador;
    private Socket socket;
    private boolean usuarioValidado = true;
    ObjectOutputStream out;
    ObjectInputStream in;
    final String host = "localhost";

    public void conectar() {
        try {
            socket = new Socket(host, puerto);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                notificarALaInterfaz((Mensaje) in.readObject()) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void notificarALaInterfaz(Mensaje mensaje) throws Exception{
        this.setChanged();
        this.notifyObservers(mensaje);
        this.clearChanged();
    }

    public void enviarNombreUsuarioAlServidor(String texto) throws Exception {
        try {
            out.writeObject(new Mensaje("String", texto));
            out.flush();
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
