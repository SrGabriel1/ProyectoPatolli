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
public class MensajeALobby implements Serializable {
    String codigo;
    String mensaje;
    Object contenido;

    public MensajeALobby() {
    }
    
    
    public MensajeALobby(String codigo, String mensaje, Object contenido) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.contenido = contenido;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Object getContenido() {
        return contenido;
    }

    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }
    
    

    
    
    
    
}
