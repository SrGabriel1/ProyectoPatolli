/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import Entidades.CondicionesPartida;
import Entidades.SolicitudALobby;
import GUIs.vistaCrearJuego;
import GUIs.vistaInicio;
import GUIs.vistaJuego;
import GUIs.vistaLobby;
import GUIs.vistaRegistro;
import GUIs.vistaRegistro2;
import conexion.Cliente;
import mensajes.Mensaje;

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
    
    public void cambiaraVentanaRegistro(vistaJuego actual){
        vistaRegistro nuevo=new vistaRegistro(this);
        actual.dispose();
        cliente.addObserver(nuevo);
        nuevo.setVisible(true);
    }
    
    public void cambiaraVentanaRegistro2(vistaJuego actual){
        vistaRegistro2 nuevo=new vistaRegistro2(this);
        actual.dispose();
        nuevo.setVisible(true);
    }
    
    public void cambiaraVentanaCrearJuego( vistaRegistro2 actual,CondicionesPartida condiciones){
        vistaCrearJuego nuevo=new vistaCrearJuego(this,condiciones);
        actual.dispose();
        nuevo.setVisible(true);
    }
    
    public void cambiaraVentanaLobby(vistaCrearJuego actual,CondicionesPartida condiciones){
        vistaLobby nuevo=new vistaLobby(this);
        cliente.mandarMensajeAlServidor(new Mensaje("CrearLobby", condiciones));
        cliente.addObserver(nuevo);
        actual.dispose();
        nuevo.setVisible(true);
    }
    
    public void registrarUsuarioEnLobby(SolicitudALobby solicitud){
        Mensaje mensaje=new Mensaje("UnirseALobby",solicitud);
        cliente.mandarMensajeAlServidor(mensaje);
    }
    
    public void cambiaraVentanaLobby(vistaRegistro actual){
        vistaLobby nuevo=new vistaLobby(this);
        cliente.deleteObserver(actual);
        cliente.addObserver(nuevo);
        actual.dispose();
        nuevo.setVisible(true);
    }
    
}
