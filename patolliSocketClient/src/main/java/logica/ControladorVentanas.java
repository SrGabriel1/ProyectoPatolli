/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import GUIs.vistaInicio;
import GUIs.vistaJuego;
import conexion.Cliente;

/**
 *
 * @author USER
 */
public class ControladorVentanas {
    Thread hiloCliente;
    Cliente cliente;

    public ControladorVentanas() {
        this.cliente = new Cliente();
    }
    
    public void cambiaraVentanaJuego(vistaInicio actual){
            vistaJuego nuevo=new vistaJuego(this);
            cliente.conectar();
            Thread hiloCliente = new Thread(cliente);
            hiloCliente.start();
            actual.dispose();
            nuevo.setVisible(true);
    }
    
}
