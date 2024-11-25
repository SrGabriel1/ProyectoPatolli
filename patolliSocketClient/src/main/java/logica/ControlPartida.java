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
        this.fichasEnTablero = new boolean[numJugadores];
        this.jugadores = new ArrayList<>();
        this.casillasInicialesPorJugador = new ArrayList<>();

        // Obtener casillas amarillas
        List<Integer> casillasAmarillas = tablero.getCasillasAmarillas();
        if (casillasAmarillas == null || casillasAmarillas.isEmpty()) {
            casillasAmarillas = generarCasillasAmarillasDefault(numJugadores);
        }

        for (int i = 0; i < numJugadores; i++) {
            int posicionInicial = casillasAmarillas.get(i);
            casillasInicialesPorJugador.add(posicionInicial);
            posicionesJugadores[i] = -1; // Indica que no están en el tablero
        }

        // Inicializar jugadores
        for (int i = 0; i < numJugadores; i++) {
            Jugador jugador = new Jugador();
            jugador.setNombre("Jugador " + (i + 1));
            jugador.setFondos(1000);
            jugador.setApuesta(0);

            List<Ficha> fichas = new ArrayList<>();
            Ficha ficha = new Ficha(coloresJugadores[i]);
            ficha.setPosicion(-1);
            fichas.add(ficha);
            colocarFichaInicial(i);
            
            jugador.setFichas(fichas);
            jugadores.add(jugador);
        }
    }

    // Método auxiliar para generar posiciones por defecto
    private List<Integer> generarCasillasAmarillasDefault(int numJugadores) {
        List<Integer> casillasDefault = new ArrayList<>();
        casillasDefault.add(54); // Arriba
        casillasDefault.add(24); // Abajo
        casillasDefault.add(9); // Izquierda
        casillasDefault.add(52); // Derecha

//        // Si hay más jugadores que casillas amarillas
//        while (casillasDefault.size() < numJugadores) {
//            casillasDefault.add(casillasDefault.size() + 1);
//        }

        return casillasDefault;
    }

    public void moverFichaJugador(int jugador, int pasos) {
        int posicionActual = tablero.moverFicha(posicionesJugadores[jugador], pasos, coloresJugadores[jugador]);
        posicionesJugadores[jugador] = posicionActual;
    }

    private void colocarFichaInicial(int jugador) {
        int posicionInicial = casillasInicialesPorJugador.get(jugador);
        posicionesJugadores[jugador] = posicionInicial;
        fichasEnTablero[jugador] = true;
        
        // Mover la ficha gráficamente al tablero
        tablero.colocarFichaInicial(posicionInicial, coloresJugadores[jugador]);
    }

    public boolean esFichaEnTablero(int jugador) {
        return fichasEnTablero[jugador];
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
