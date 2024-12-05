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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /**
     * Método para conectar el cliente al servidor. Establece un socket en el
     * host y puerto definidos, y configura los streams de entrada y salida para
     * la comunicación.
     */
    public void conectar() {
        try {
            socket = new Socket(host, puerto);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que contiene la lógica principal del cliente. Escucha
     * continuamente los mensajes provenientes del servidor y notifica a los
     * observadores.
     */
    @Override
    public void run() {
        try {
            while (true) {
                notificarALaInterfaz((Mensaje) in.readObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Notifica a los observadores registrados con el mensaje recibido desde el
     * servidor.
     *
     * @param mensaje El mensaje recibido del servidor.
     * @throws Exception Si ocurre un error al notificar a los observadores.
     */
    public void notificarALaInterfaz(Mensaje mensaje) throws Exception {
        this.setChanged();
        this.notifyObservers(mensaje);
        this.clearChanged();
    }

    /**
     * Envía un mensaje al servidor.
     *
     * @param mensaje El mensaje que será enviado al servidor.
     */
    public void mandarMensajeAlServidor(Mensaje mensaje) {
        try {
            out.writeObject(mensaje);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
