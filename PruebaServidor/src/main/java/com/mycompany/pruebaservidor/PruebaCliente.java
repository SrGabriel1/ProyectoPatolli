/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pruebaservidor;

import Excepciones.ClienteException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

/**
 *
 * @author USER
 */
public class PruebaCliente extends Observable implements Runnable {

    private int puerto = 4444;
    private String nombreJugador;
    private Socket sc;
    private boolean usuarioValidado = true;
    ObjectOutputStream outObject;
    ObjectInputStream inObject;
    final String host = "localhost";

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    @Override
    public void run() {
        try {
            sc = new Socket(host, puerto);
            outObject = new ObjectOutputStream(sc.getOutputStream());
            inObject = new ObjectInputStream(sc.getInputStream());
            while (true) {
                notificarALaInterfaz((Mensaje) inObject.readObject() );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void notificarALaInterfaz(Mensaje mensaje) throws ClienteException{
        this.setChanged();
        this.notifyObservers(mensaje);
        this.clearChanged();
    }

    public void enviarNombreUsuarioAlServidor(String texto) throws Exception {
        try {
            outObject.writeObject(new Mensaje("String", texto));
            outObject.flush();
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
