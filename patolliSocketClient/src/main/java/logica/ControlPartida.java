/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import Tablero.PatolliPanel;
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
    private int numJugadores;
    int numFichasPorJugador;

    /**
     * Constructor que inicializa los elementos de la partida.
     *
     * @param tablero Referencia al panel del tablero del juego.
     * @param numJugadores Número de jugadores en la partida.
     */
    public ControlPartida(PatolliPanel tablero, int numJugadores) {
        this.numJugadores = numJugadores;
        this.tablero = tablero;
        this.posicionesJugadores = new int[numJugadores];
        this.fichasEnTablero = new boolean[numJugadores];
        this.jugadores = new ArrayList<>();
        this.casillasInicialesPorJugador = new ArrayList<>();

        iniciarCasillasAmarillas(numJugadores);

        iniciarJugadores(numJugadores);
    }

    /**
     * Inicializa los jugadores y sus fichas.
     *
     * @param numJugadores Número de jugadores en la partida.
     */
    private void iniciarJugadores(int numJugadores) {
        switch (numJugadores) {
            case 2:
                numFichasPorJugador = 2;
            case 3:
                numFichasPorJugador = 4;
            case 4:
                numFichasPorJugador = 5;
            default:
                numFichasPorJugador = 1;
        }

        // Inicializar jugadores
        for (int i = 0; i < numJugadores; i++) {
            Jugador jugador = new Jugador();
            jugador.setNombre("Jugador " + (i + 1));
            jugador.setFondos(1000);
            jugador.setApuesta(0);
            List<Ficha> fichas = new ArrayList<>();
            for (int j = 0; j < numFichasPorJugador; j++) {
                Ficha ficha = new Ficha(coloresJugadores[i]);
                ficha.setPosicion(-1);
                fichas.add(ficha);
            }
            jugador.setFichas(fichas);
            jugadores.add(jugador);
            colocarFichaInicial(i);
        }
    }

    /**
     * Configura las casillas iniciales y finales para cada jugador en el
     * tablero.
     *
     * @param numJugadores Número de jugadores en la partida.
     */
    public void iniciarCasillasAmarillas(int numJugadores) {
        List<Integer> casillasAmarillas = tablero.getCasillasAmarillas();
        if (casillasAmarillas == null || casillasAmarillas.isEmpty()) {
            casillasAmarillas = generarCasillasAmarillasDefault();
        }
        casillasFinal.add(27);
        casillasFinal.add(61);
        casillasFinal.add(44);
        casillasFinal.add(10);
        for (int i = 0; i < numJugadores; i++) {
            int posicionInicial = casillasAmarillas.get(i);
            casillasInicialesPorJugador.add(posicionInicial);
            posicionesJugadores[i] = -1;
        }
    }

    /**
     * Genera una configuración por defecto para las casillas amarillas.
     *
     * @return Lista de posiciones de casillas amarillas por defecto.
     */
    private List<Integer> generarCasillasAmarillasDefault() {
        List<Integer> casillasDefault = new ArrayList<>();
        casillasDefault.add(61); // Arriba
        casillasDefault.add(27); // Abajo
        casillasDefault.add(10); // Izquierda
        casillasDefault.add(44); // Derecha

        return casillasDefault;
    }

    /**
     * Método que maneja el número de vueltas completadas por un jugador.
     *
     * @param jugador Índice del jugador.
     */
    public void numVueltasMethod(int jugador) {
        int totalCasillas = 68;

        for (int i = 0; i < posicionesJugadores.length; i++) {
            int casillaInicial = casillasInicialesPorJugador.get(i);
            int casillaFinal = casillasFinal.get(i);
            int posicionActual = posicionesJugadores[i];

            int distanciaDesdeInicial = (posicionActual - casillaInicial + totalCasillas) % totalCasillas;
            int distanciaHastaFinal = (casillaFinal - casillaInicial + totalCasillas) % totalCasillas;

            if (distanciaDesdeInicial >= distanciaHastaFinal) {
                tablero.removerFicha(posicionActual);
                System.out.println("Posicion actual: " + posicionActual);
                colocarFichaInicial(jugador);
                int vueltas = jugadores.get(jugador).getNumVueltas();
                vueltas++;
                jugadores.get(jugador).setNumVueltas(vueltas);
                System.out.println("Numero de vueltas: " + vueltas);
            }
        }
    }

    /**
     * Mueve la ficha de un jugador en el tablero.
     *
     * @param jugador Índice del jugador.
     * @param pasos Número de pasos a mover.
     * @return {@code true} si hay un ganador; de lo contrario, {@code false}.
     */
    public boolean moverFichaJugador(int jugador, int pasos) {

        int posicionActual = tablero.moverFicha(posicionesJugadores[jugador], pasos, coloresJugadores[jugador]);
        posicionesJugadores[jugador] = posicionActual;
        if (jugadores.get(jugador).getNumVueltas() < numFichasPorJugador) {
            numVueltasMethod(jugador);
        }
        return verificarGanador();

    }

    /**
     * Coloca la ficha inicial para un jugador.
     *
     * @param jugador Índice del jugador.
     */
    private void colocarFichaInicial(int jugador) {
        int posicionInicial = casillasInicialesPorJugador.get(jugador);
        posicionesJugadores[jugador] = posicionInicial;
        fichasEnTablero[jugador] = true;

        tablero.colocarFichaInicial(posicionInicial, coloresJugadores[jugador]);
    }

    /**
     * Verifica si el jugador tiene alguna ficha en el tablero.
     *
     * @param jugador Índice del jugador.
     * @return true si el jugador tiene una ficha en el tablero de lo contrario
     * un false.
     */
    public boolean esFichaEnTablero(int jugador) {
        return fichasEnTablero[jugador];
    }

    /**
     * Obtiene el color asignado al jugador actual.
     *
     * @param jugadorActual Índice del jugador actual.
     * @return Color del jugador actual como una cadena de texto.
     */
    public String getJugadorActualColor(int jugadorActual) {
        return coloresJugadores[jugadorActual];
    }

    /**
     * Lanza cinco dados virtuales y calcula el número de pasos en función de
     * los resultados. Cada dado tiene un 50% de probabilidad de resultar en
     * éxito.
     *
     * @return Número de pasos a mover, calculados en función de los resultados
     * de los dados.
     */
    public int lanzarDados() {
        boolean[] resultados = new boolean[5];
        for (int i = 0; i < resultados.length; i++) {
            resultados[i] = Math.random() < 0.5;
        }
        int pasos = 0;
        for (boolean resultado : resultados) {
            if (resultado) {
                pasos++;
            }
        }
        return 0;
    }

    /**
     * Genera cañas aleatorias simulando un lanzamiento de dados.
     *
     * @return Arreglo de booleanos representando los resultados del
     * lanzamiento.
     */
    public boolean[] generarCañasAleatorias() {
        boolean[] resultados = new boolean[5];
        for (int i = 0; i < resultados.length; i++) {
            resultados[i] = Math.random() < 0.5;
        }
        return resultados;
    }

    /**
     * Calcula los pasos basados en los resultados de las cañas.
     *
     * @param resultados Resultados del lanzamiento de las cañas.
     * @return Número de pasos a mover.
     */
    public int calcularPasos(boolean[] resultados) {
        int pasos = 0;
        for (boolean resultado : resultados) {
            if (resultado) {
                pasos++;
            }
        }
        if (pasos == 5) {
            pasos = 10;
        }
        return pasos;
    }

    /**
     * Verifica si hay un jugador ganador.
     *
     * @return {@code true} si hay un ganador; de lo contrario, {@code false}.
     */
    public boolean verificarGanador() {
        int totalCasillas = 68;
        for (int i = 0; i < posicionesJugadores.length; i++) {
            int casillaInicial = casillasInicialesPorJugador.get(i);
            int casillaFinal = casillasFinal.get(i);
            int posicionActual = posicionesJugadores[i];

            int distanciaDesdeInicial = (posicionActual - casillaInicial + totalCasillas) % totalCasillas;
            int distanciaHastaFinal = (casillaFinal - casillaInicial + totalCasillas) % totalCasillas;

            if (distanciaDesdeInicial >= distanciaHastaFinal) {
                return true;
            }
        }
        return false;
    }
}
