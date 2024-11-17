package logica_sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import logica.Observable;
import logica.Reglamento;

public class Servidor extends Observable implements Runnable {

    private ArrayList<Socket> clientes;
    private int puerto, turno;
    private Reglamento juego;
    private boolean iniciado;
    private ServerSocket servidor;
    private int ganador;
    private Socket sc;

    public Servidor(int puerto) throws IOException {
        this.puerto = puerto;
        clientes = new ArrayList<>();
        iniciado = false;
        sc = null;
        turno = 0;
        ganador = -1;
        //Inicio del socket server
        servidor = new ServerSocket(puerto);
        System.out.println("Servidor iniciado");
    }

    @Override
    public void run() {

        String nombre = "";
        //Mientras el juego no haya iniciado se aceptan clientes(jugadores)
        while (!iniciado) {

            try {//Recibe un nuevo cliente (jugador)
                sc = servidor.accept();
            } catch (IOException ex) {
                System.out.println(ex.getMessage() + " error al aceptar socket");
            }
            //Si ya inició el juego o si ya no se aceptan nuevos jugadores
            if (clientes.size() >= 4 || iniciado) {
                try {//Se cierra el socket y se inicia el juego
                    sc.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage() + " error al cerrar socket");
                }
            } else {//Sino ha iniciado y caben más jugadores se ingresa a la lista
                try {//Se pide un nombre al jugador para identificarlo
                    nombre = new DataInputStream(sc.getInputStream()).readUTF();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage() + " error al obtener nombre");
                }
                this.notificar(nombre);//Se notifica a la interface del server
                clientes.add(sc);//Se añade el cliente a la lista
                System.out.println("Nuevo cliente conectado");
            }
        }

        System.out.println("Juego iniciado desde el server");
        while (juego.isFin()==false) {

            try {//si el socket no se ha desconectado recibe sus datos
                if (!juego.getJugadores().get(turno).isEliminado()) {
                    juego = (Reglamento) new ObjectInputStream(clientes.get(turno).getInputStream()).readObject();
                }
            } catch (IOException | ClassNotFoundException ex) {
                //si el socket se desconectó mueve sus piezas solo
                if (!juego.getJugadores().get(turno).isEliminado()) {
                    juego.actualizarCambios(juego.getDado().tirarDados(), -1);
                }
                System.out.println("Cliente desconectado, moviendo sus fichas");
            }

            try {//Esperar 1 segundos para que el cambio no sea brusco
                Thread.sleep(1000);
            } catch (InterruptedException ex1) {
            }

            System.out.println("Tablero recibido desde el jugador: " + juego.getJugadores().get(turno).getNombre());
            actualizarInfoClientes(); //Mandar el juego actualizado a todos los jugadores
            turno = juego.getTurno(); //Actualiza el turno
            System.out.println("Turno en el juego: " + juego.getTurno() + " Turno en el sv: " + turno);
        }
        //Cuando sale del ciclo es que ya hay ganador
        actualizarInfoClientes();

    }

    /**
     * Envia a los clientes el juego con la info actualizada
     */
    public void actualizarInfoClientes() {
        //Recorre los sockets conectado mientras no hayan sido cerrados
        for (Socket cliente : clientes) {
            if (!cliente.isClosed()) {
                try {//Envia la clase Reglamento con la info actualizada
                    new ObjectOutputStream(cliente.getOutputStream()).writeObject(juego);
                } catch (IOException ex) {
                }
            }
        }
    }

    /**
     * Asigna un número a cada cliente para identificarlo
     */
    public void asignarNumeroJugador() {
        for (int i = 0; i < clientes.size(); i++) {
            try {
                new DataOutputStream(clientes.get(i).getOutputStream()).writeUTF(i + "");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ArrayList<Socket> getClientes() {
        return clientes;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setJuego(Reglamento juego) {
        this.juego = juego;
    }

    /**
     * Indica cuando se inicia el juego desde la interface servidor
     *
     * @param iniciado variable que indica si el juego se debe iniciar(true)
     */
    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
        try {//Conecta y desconecta un socket a este sv para que deje de esperar
            //nuevos clientes, indicando que el juego debe iniciar
            Socket sck = new Socket(servidor.getInetAddress(), this.puerto);
            sck.close();
            sc.close();
        } catch (IOException ex) {
            System.out.println("Es en el set iniciado " + ex.getMessage());
        }
    }

    /**
     * Avisa a todos los jugadores que el juego ha iniciado, para estar listos
     */
    public void avisarInicio() {
        for (Socket cliente : clientes) {
            try {
                new DataOutputStream(cliente.getOutputStream()).writeBoolean(true);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
