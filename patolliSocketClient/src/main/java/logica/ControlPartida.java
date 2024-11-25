/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import Entidades.Ficha;
import Entidades.Jugador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class ControlPartida {

    private int[] posicionesJugadores;
    private boolean[] fichasEnTablero; // Nuevo: Controla qué fichas están en juego
    private String[] coloresJugadores = {"naranja", "roja", "amarilla", "morada"};
    private PatolliPanel tablero;
    private List<Jugador> jugadores;
    private List<Integer> casillasInicialesPorJugador; // Nuevo: Guarda la casilla inicial de cada jugador

    public ControlPartida(PatolliPanel tablero, int numJugadores) {
        this.tablero = tablero;
        this.posicionesJugadores = new int[numJugadores];
        this.fichasEnTablero = new boolean[numJugadores]; // Inicialmente false
        this.jugadores = new ArrayList<>();
        this.casillasInicialesPorJugador = new ArrayList<>();

        // Asignar casillas amarillas a jugadores
        List<Integer> casillasAmarillas = tablero.getCasillasAmarillas();
        for (int i = 0; i < numJugadores; i++) {
            casillasInicialesPorJugador.add(casillasAmarillas.get(i % casillasAmarillas.size()));
            posicionesJugadores[i] = -1; // -1 indica que la ficha no está en el tablero
        }

        // Inicializar jugadores
        for (int i = 0; i < numJugadores; i++) {
            Jugador jugador = new Jugador();
            jugador.setNombre("Jugador " + (i + 1));
            jugador.setFondos(1000);
            jugador.setApuesta(0);

            List<Ficha> fichas = new ArrayList<>();
            Ficha ficha = new Ficha(coloresJugadores[i]);
            ficha.setPosicion(-1); // La ficha empieza fuera del tablero
            fichas.add(ficha);

            jugador.setFichas(fichas);
            jugadores.add(jugador);
        }
    }

    public void turnoJugador(int jugador, int pasos) {
        // Si la ficha no está en el tablero y hay pasos válidos, colocarla
        if (!fichasEnTablero[jugador] && pasos > 0) {
            colocarFichaInicial(jugador);
            return;
        }

        // Si la ficha ya está en el tablero, moverla
        if (fichasEnTablero[jugador]) {
            int posicionActual = posicionesJugadores[jugador];
            int nuevaPosicion = calcularNuevaPosicion(posicionActual, pasos);

            // Mover la ficha en el tablero
            tablero.moverFicha(posicionActual, nuevaPosicion - posicionActual, coloresJugadores[jugador]);
            posicionesJugadores[jugador] = nuevaPosicion;
        }
    }

    private int calcularNuevaPosicion(int posicionActual, int pasos) {
        int totalCasillas = tablero.getCasillas().size();
        return (posicionActual + pasos) % totalCasillas; // Movimiento circular
    }

    private void colocarFichaInicial(int jugador) {
        int posicionInicial = casillasInicialesPorJugador.get(jugador);
        posicionesJugadores[jugador] = posicionInicial;
        fichasEnTablero[jugador] = true;
        tablero.moverFicha(posicionInicial, 0, coloresJugadores[jugador]);
    }

    public boolean esFichaEnTablero(int jugador) {
        return fichasEnTablero[jugador];
    }

    public void lanzarDadosYMover(int pasos, int jugadorActual) {
        turnoJugador(jugadorActual, pasos); // Mueve la ficha del jugador actual
        jugadorActual = (jugadorActual + 1) % posicionesJugadores.length; // Cambia al siguiente jugador
    }

    public String getJugadorActualColor(int jugadorActual) {
        return coloresJugadores[jugadorActual]; // Devuelve el color del jugador actual
    }

    public int lanzarDados() {
        boolean[] resultados = new boolean[5];
        for (int i = 0; i < resultados.length; i++) {
            resultados[i] = Math.random() < 0.5; // 50% de probabilidad de punto
        }
        int pasos = 0;
        for (boolean resultado : resultados) {
            if (resultado) {
                pasos++;
            }
        }
        return pasos > 0 ? pasos : 10; // Si no hay puntos, mueve 10 pasos como regla alternativa
    }

    // Genera resultados aleatorios de las cañas
    public boolean[] generarCañasAleatorias() {
        boolean[] resultados = new boolean[5];
        for (int i = 0; i < resultados.length; i++) {
            resultados[i] = Math.random() < 0.5; // 50% de probabilidad de punto
        }
        return resultados;
    }

    // Calcula los pasos en función de los resultados de las cañas
    public int calcularPasos(boolean[] resultados) {
        int pasos = 0;
        for (boolean resultado : resultados) {
            if (resultado) {
                pasos++;
            }
        }
        return pasos;
    }

    public void iniciarPartida() {
    }

    public void verificarGanador() {
    }

    public void mostrarVictoria() {
    }
}
