/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebaservidor;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author USER
 */
public class DatosUsuario implements Serializable{
    private Socket socket;
    private String nombre;
    private ObjectOutputStream out;

    public DatosUsuario() {
    }

    public DatosUsuario(Socket socket, String nombre, ObjectOutputStream out) {
        this.socket = socket;
        this.nombre = nombre;
        this.out = out;
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

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public String toString() {
        return "DatosUsuario{" + "socket=" + socket + ", nombre=" + nombre + ", out=" + out + '}';
    }
    
    
    
}
