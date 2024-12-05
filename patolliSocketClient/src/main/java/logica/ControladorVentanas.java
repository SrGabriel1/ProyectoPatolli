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
    CondicionesPartida condiciones;

    /**
     * Constructor de la clase. Inicializa un cliente para la comunicación con
     * el servidor.
     */
    public ControladorVentanas() {
        this.cliente = new Cliente();
    }

    /**
     * Cambia de la ventana vistaInicio a vistaJuego.
     *
     * @param actual La ventana actual (vistaInicio).
     */
    public void cambiaraVentanaJuego(vistaInicio actual) {
        vistaJuego nuevo = new vistaJuego(this);
        cliente.conectar();
        Thread hiloCliente = new Thread(cliente);
        hiloCliente.start();
        actual.dispose();
        nuevo.setVisible(true);
    }

    /**
     * Cambia de la ventana vistaJuego a vistaEntrarALobby.
     *
     * @param actual La ventana actual (vistaJuego).
     */
    public void cambiaraVentanaRegistro(vistaJuego actual) {
        vistaEntrarALobby nuevo = new vistaEntrarALobby(this);
        actual.dispose();
        cliente.addObserver(nuevo);
        nuevo.setVisible(true);
    }

    /**
     * Cambia de la ventana vistaJuego a vistaSeleccionarNombre.
     *
     * @param actual La ventana actual (vistaJuego).
     */
    public void cambiaraVentanaRegistro2(vistaJuego actual) {
        vistaSeleccionarNombre nuevo = new vistaSeleccionarNombre(this);
        actual.dispose();
        nuevo.setVisible(true);
    }

    /**
     * Cambia de la ventana vistaSeleccionarNombre a vistaCrearJuego.
     *
     * @param actual La ventana actual (vistaSeleccionarNombre).
     * @param condiciones Objeto que contiene las condiciones de la partida.
     */
    public void cambiaraVentanaCrearJuego(vistaSeleccionarNombre actual, CondicionesPartida condiciones) {
        vistaCrearJuego nuevo = new vistaCrearJuego(this, condiciones);
        actual.dispose();
        nuevo.setVisible(true);
    }

    /**
     * Cambia de la ventana vistaCrearJuego a vistaLobby.
     *
     * @param actual La ventana actual (vistaCrearJuego).
     * @param condiciones Objeto que contiene las condiciones de la partida.
     */
    public void cambiaraVentanaLobby(vistaCrearJuego actual, CondicionesPartida condiciones) {
        vistaLobby nuevo = new vistaLobby(this);
        cliente.mandarMensajeAlServidor(new Mensaje("CrearLobby", condiciones));
        System.out.println("Dinero" + condiciones.getSemillas());
        cliente.addObserver(nuevo);
        actual.dispose();
        nuevo.setVisible(true);
    }

    /**
     * Cambia de la ventana vistaLobby a vistaTablero.
     *
     * @param actual La ventana actual (vistaLobby).
     * @param jugadores Número de jugadores en la partida.
     * @param codigo Código del juego.
     * @throws Exception Si ocurre un error durante la transición.
     */
    public void cambiaraVentanaVistaTablero(vistaLobby actual, int jugadores, String codigo) throws Exception {

        cliente.mandarMensajeAlServidor(new Mensaje("JugadorListo", ""));
        vistaTablero nuevo = new vistaTablero(jugadores, codigo, this);
        cliente.deleteObserver(actual);
        cliente.addObserver(nuevo);
        actual.dispose();
        nuevo.setVisible(true);
    }

    /**
     * Registra a un usuario en el lobby enviando una solicitud al servidor.
     *
     * @param solicitud La solicitud para unirse al lobby.
     */
    public void registrarUsuarioEnLobby(SolicitudALobby solicitud) {
        Mensaje mensaje = new Mensaje("UnirseALobby", solicitud);
        cliente.mandarMensajeAlServidor(mensaje);
    }

    /**
     * Cambia de la ventana vistaEntrarALobby a vistaLobby.
     *
     * @param actual La ventana actual (vistaEntrarALobby).
     */
    public void cambiaraVentanaLobby(vistaEntrarALobby actual) {
        vistaLobby nuevo = new vistaLobby(this);
        cliente.deleteObserver(actual);
        cliente.addObserver(nuevo);
        actual.dispose();
        nuevo.setVisible(true);
    }

    /**
     * Envía un mensaje al servidor.
     *
     * @param mensaje El mensaje a enviar.
     */
    public void mandarMensajeAServidor(Mensaje mensaje) {
        cliente.mandarMensajeAlServidor(mensaje);
    }

    /**
     * Cambia a la ventana vistaPartidaGanada.
     *
     * @param actual La ventana actual (vistaTablero).
     */
    public void cambiarPartidaGanada(vistaTablero actual) {
        vistaPartidaGanada p = new vistaPartidaGanada();
        p.setVisible(true);
        actual.dispose();
    }

    /**
     * Cambia a la ventana vistaPartidaPerdida.
     *
     * @param actual La ventana actual (vistaTablero).
     */
    public void cambiarPartidaPerdida(vistaTablero actual) {
        vistaPartidaPerdida p = new vistaPartidaPerdida();
        p.setVisible(true);
        actual.dispose();
    }

}
