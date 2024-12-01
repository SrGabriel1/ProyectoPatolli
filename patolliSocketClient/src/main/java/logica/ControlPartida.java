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
    private String[] coloresJugadores = {"morada", "amarilla", "roja", "naranja"};
    private PatolliPanel tablero;
    private List<Jugador> jugadores;
    private List<Integer> casillasInicialesPorJugador; // Nuevo: Guarda la casilla inicial de cada jugador
    private List<Integer> casillasFinal = new ArrayList<>();

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
        casillasFinal.add(27);
        casillasFinal.add(61);
        casillasFinal.add(44);
        casillasFinal.add(10);
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
        casillasDefault.add(61); // Arriba
        casillasDefault.add(27); // Abajo
        casillasDefault.add(10); // Izquierda
        casillasDefault.add(44); // Derecha

//        Si hay más jugadores que casillas amarillas
//        while (casillasDefault.size() < numJugadores) {
//            casillasDefault.add(casillasDefault.size() + 1);
//        }
        return casillasDefault;
    }

    public boolean moverFichaJugador(int jugador, int pasos) {

        int posicionActual = tablero.moverFicha(posicionesJugadores[jugador], pasos, coloresJugadores[jugador]);

        posicionesJugadores[jugador] = posicionActual;

//        if (verificarGanador() == true) {
//            panelPartidaGanada p = new panelPartidaGanada();
//            p.setVisible(true);
//        }
        return verificarGanador();
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

    public boolean verificarGanador() {
        for (int i = 0; i < posicionesJugadores.length; i++) {
            if (posicionesJugadores[i] == casillasFinal.get(i)) {
                System.out.println("si entro a la condicion");
                return true; 
            }
        }
        return false;
    }
}
