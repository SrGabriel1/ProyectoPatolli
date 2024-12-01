/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mensajes;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class MensajeMovimiento implements Serializable{
    int usuarioTira;
    int pasos;

    public MensajeMovimiento() {
    }
    
    

    public MensajeMovimiento(int usuarioTira, int pasos) {
        this.usuarioTira = usuarioTira;
        this.pasos = pasos;
    }

    public MensajeMovimiento(int usuarioTira) {
        this.usuarioTira = usuarioTira;
    }

    public int getUsuarioTira() {
        return usuarioTira;
    }

    public void setUsuarioTira(int usuarioTira) {
        this.usuarioTira = usuarioTira;
    }

    public int getPasos() {
        return pasos;
    }

    public void setPasos(int pasos) {
        this.pasos = pasos;
    }
    
    
    
}
