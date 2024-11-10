/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebaservidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author USER
 */
public class DatosUsuario implements Serializable {

    private Socket socket;
    private String nombre;
    private ObjectOutputStream out;

    public DatosUsuario() {
    }

    public DatosUsuario(String nombre) {
        this.nombre = nombre;
    }

    public DatosUsuario(Socket socket, String nombre) throws IOException {
        this.socket = socket;
        this.nombre = nombre;
        this.out = new ObjectOutputStream(socket.getOutputStream());

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ObjectOutputStream getOutputStream() {
        return out;
    }
}
