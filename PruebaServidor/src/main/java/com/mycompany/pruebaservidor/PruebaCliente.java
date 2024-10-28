/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pruebaservidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Observable;

/**
 *
 * @author USER
 */
public class PruebaCliente extends Observable implements Runnable{
    
    private int puerto = 4444;
    private String nombreJugador;
    private Socket sc;

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    @Override
    public void run() {
        final String host = "localhost";

        try {
            sc = new Socket(host, puerto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void enviarNombreUsuarioAlServidor(String texto) throws Exception {
        try {
            if (sc != null && !sc.isClosed()) {
                DataOutputStream dos = new DataOutputStream(sc.getOutputStream());
                dos.writeUTF(texto);
                dos.flush();
            }
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
