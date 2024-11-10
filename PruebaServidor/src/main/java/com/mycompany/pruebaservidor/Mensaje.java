/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebaservidor;

/**
 *
 * @author USER
 */
import java.io.Serializable;

public class Mensaje implements Serializable {
    private String tipo;
    private Object contenido;

    public Mensaje(String tipo, Object contenido) {
        this.tipo = tipo;
        this.contenido = contenido;
    }

    public String getTipo() {
        return tipo;
    }

    public Object getContenido() {
        return contenido;
    }
}
