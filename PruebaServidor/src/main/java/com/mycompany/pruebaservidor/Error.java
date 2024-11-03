/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebaservidor;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Error implements Serializable{
    private String tipo;
    private String mensaje;

    public Error() {
    }

    public Error(String tipo, String mensaje) {
        this.tipo = tipo;
        this.mensaje = mensaje;
    }
    
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
