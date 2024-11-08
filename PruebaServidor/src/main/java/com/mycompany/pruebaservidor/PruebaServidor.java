/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pruebaservidor;

import java.util.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class PruebaServidor extends Observable implements Runnable {

    private ArrayList<DatosUsuario> clientes = new ArrayList<>();
    private int puerto = 4444;
    ServerSocket servidor = null;
    Socket sc = null;
    ObjectInputStream in;
    ObjectOutputStream outObject;
    boolean listening = true;

    @Override
    public void run() {
        agregarUsuariosAPartida();

        partidaEnJuego();
    }

    public void agregarUsuariosAPartida() {
        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            while (listening) {
                Socket clientSocket = servidor.accept();
                System.out.println("Nuevo cliente conectado");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validarUsuario(String nombre) {
        for (DatosUsuario cliente : clientes) {
            if (cliente.getNombre().equalsIgnoreCase(nombre)) {
                return false;
            }
        }
        return true;
    }

    public void notificarALaInterfaz(Object o) {
        this.setChanged();
        this.notifyObservers(o);
        this.clearChanged();
    }

    private class ClientHandler implements Runnable {

        private Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;

        }

        @Override
        public void run() {

            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                while (true) {
                    Mensaje mensajeRecibido = (Mensaje) in.readObject();
                    if (mensajeRecibido.getTipo().equals("String")) {
                        String mensaje = (String) mensajeRecibido.getContenido();
                        if (validarUsuario(mensaje)) {
                            clientes.add(new DatosUsuario(sc, mensaje));
                            notificarALaInterfaz(mensaje);
                        } else {
                            out.writeObject(new Mensaje("Error", new Error("Usuario con nombre repetido", "Otro usuario ya cuenta con ese nombre")));
                            out.flush();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void partidaEnJuego() {

    }
}
