package logica_sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import logica.Observable;
import logica.Reglamento;
import utileria.Ficha;

public class Cliente extends Observable implements Runnable {

    private int puerto, numJugador;
    private String nombre;
    private Socket sc;
    private Reglamento juego;
    private boolean iniciado;
    private String HOST;

    public Cliente(int puerto, String nombre, String HOST) throws IOException {
        this.puerto = puerto;
        this.nombre = nombre;
        juego = null;
        iniciado = false;
        this.HOST = HOST;
        sc = new Socket(HOST, puerto);
    }

    @Override
    public void run() {

        try {
            //Se conecta al server
            //Avisa al server el nombre de este cliente
            new DataOutputStream(sc.getOutputStream()).writeUTF(nombre);
            //El server asigna un número de jugador para identificarlo
            String num = new DataInputStream(sc.getInputStream()).readUTF();
            numJugador = Integer.parseInt(num);
            System.out.println("numJugador: " + numJugador);
            //Si el juego no ha iniciado, espera a que inicie
            if (!iniciado) {
                //Esperando que el server avise del inicio del juego
                iniciado = new DataInputStream(sc.getInputStream()).readBoolean();
                System.out.println("Iniciado " + iniciado);
            }
            //Mientras el juego esté iniciado recibe actualizaciones
            while (iniciado) {
                //Recibe actualización de todo el juego
                juego = (Reglamento) new ObjectInputStream(sc.getInputStream()).readObject();
                System.out.println("Cliente actualizado");
                //Notifica a la interface del jugador cuando es su turno
                if(juego.isFin()){
                    iniciado = !juego.isFin();
                    this.notificar(false);
                    break;
                }
                this.notificar(juego.getTurno() == numJugador);
            }

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage() + " es en el cliente");
        }

    }

    /**
     * Actualiza los movimientos de este cliente y envia la actualización al sv
     *
     * @param resultados los resultados de tirar el dado
     * @param numFicha el número de la ficha a mover, envie -1 si el jugador se
     * desconectó
     * @throws IOException por sino se puede enviar la info al server
     */
    public void actualizarJuego(int[] resultados, int numFicha) throws IOException {
        if (!juego.getJugadores().get(juego.getTurno()).isEliminado()) {
            juego.actualizarCambios(resultados, numFicha);
            new ObjectOutputStream(sc.getOutputStream()).writeObject(juego);
            System.out.println("Tablero enviado desde el cliente: " + nombre);
            if (resultados[0] == 0) {
                JOptionPane.showMessageDialog(null, "Tirada no exitosa, pagas apuesta", "Info!", JOptionPane.ERROR_MESSAGE);
            }
            this.notificar(false);
        }
    }

    public int getNumJugador() {
        return numJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public Reglamento getJuego() {
        return juego;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public ArrayList<Ficha> getFichas() {
        return juego.getJugadores().get(numJugador).getFichas();
    }

    /**
     * Cierra este cliente socket para continuar con la lógica del juego
     */
    public void cerrar() {
        try {
            this.sc.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
