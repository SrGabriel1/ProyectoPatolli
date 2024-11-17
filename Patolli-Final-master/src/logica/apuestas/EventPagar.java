/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.apuestas;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class EventPagar implements Serializable{
   
    private float cantidad;
    
    public EventPagar(float cantidad){
        this.cantidad = cantidad;
    }

    public float getCantidad() {
        return cantidad;
    }
    
}
