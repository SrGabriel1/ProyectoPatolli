/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import Entidades.CondicionesPartida;
import mensajes.SolicitudALobby;
import GUIs.vistaCrearJuego;
import GUIs.vistaInicio;
import GUIs.vistaJuego;
import GUIs.vistaLobby;
import GUIs.vistaEntrarALobby;
import GUIs.vistaPartidaGanada;
import GUIs.vistaPartidaPerdida;
import GUIs.vistaSeleccionarNombre;
import GUIs.vistaTablero;
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

    public void cambiaraVentanaJuego(vistaInicio actual) {
        vistaJuego nuevo = new vistaJuego(this);
        cliente.conectar();
        Thread hiloCliente = new Thread(cliente);
        hiloCliente.start();
        actual.dispose();
        nuevo.setVisible(true);
    }

    public void cambiaraVentanaRegistro(vistaJuego actual) {
        vistaEntrarALobby nuevo = new vistaEntrarALobby(this);
        actual.dispose();
        cliente.addObserver(nuevo);
        nuevo.setVisible(true);
    }

    public void cambiaraVentanaRegistro2(vistaJuego actual) {
        vistaSeleccionarNombre nuevo = new vistaSeleccionarNombre(this);
        actual.dispose();
        nuevo.setVisible(true);
    }

    public void cambiaraVentanaCrearJuego(vistaSeleccionarNombre actual, CondicionesPartida condiciones) {
        vistaCrearJuego nuevo = new vistaCrearJuego(this, condiciones);
        actual.dispose();
        nuevo.setVisible(true);
    }

    public void cambiaraVentanaLobby(vistaCrearJuego actual, CondicionesPartida condiciones) {
        vistaLobby nuevo = new vistaLobby(this);
        cliente.mandarMensajeAlServidor(new Mensaje("CrearLobby", condiciones));
        cliente.addObserver(nuevo);
        actual.dispose();
        nuevo.setVisible(true);
    }

    public void cambiaraVentanaVistaTablero(vistaLobby actual, String codigo) throws Exception {

        cliente.mandarMensajeAlServidor(new Mensaje("JugadorListo", ""));
        vistaTablero nuevo = new vistaTablero(2, codigo, this);
        cliente.deleteObserver(actual);
        cliente.addObserver(nuevo);
        actual.dispose();
        nuevo.setVisible(true);
    }

    public void registrarUsuarioEnLobby(SolicitudALobby solicitud) {
        Mensaje mensaje = new Mensaje("UnirseALobby", solicitud);
        cliente.mandarMensajeAlServidor(mensaje);
    }

    public void cambiaraVentanaLobby(vistaEntrarALobby actual) {
        vistaLobby nuevo = new vistaLobby(this);
        cliente.deleteObserver(actual);
        cliente.addObserver(nuevo);
        actual.dispose();
        nuevo.setVisible(true);
    }

    public void mandarMensajeAServidor(Mensaje mensaje) {
        cliente.mandarMensajeAlServidor(mensaje);
    }

    public void cambiarPartidaGanada(vistaTablero actual) {
        vistaPartidaGanada p = new vistaPartidaGanada();
        p.setVisible(true);
        actual.dispose();
    }

    public void cambiarPartidaPerdida(vistaTablero actual) {
        vistaPartidaPerdida p = new vistaPartidaPerdida();
        p.setVisible(true);
        actual.dispose();
    }

}
