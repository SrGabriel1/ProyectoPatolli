/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tablero;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import Tablero.CasillaPintar;

/**
 *
 * @author Gabriel
 */
public class TableroBuilder {

    private final int filas;
    private final int columnas;
    private final JPanel tablero;
    private final List<JLabel> casillas;
    private final TipoTablero tipoTablero;
    private final int numJugadores;
    private final List<Integer> casillasAmarillasPos = new ArrayList<>();

    /**
     * Enumeración que define los tipos de tablero disponibles.
     */
    public enum TipoTablero {
        ARRIBA, ABAJO, IZQUIERDA, DERECHA, CENTRAL
    }

    /**
     * Constructor privado de la clase TableroBuilder.
     *
     * @param filas Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     * @param tablero Panel que representa el tablero.
     * @param casillas Lista de casillas del tablero.
     * @param tipoTablero Tipo de tablero (ARRIBA, ABAJO, IZQUIERDA, DERECHA,
     * CENTRAL).
     * @param numJugadores Número de jugadores en el juego.
     */
    private TableroBuilder(int filas, int columnas, JPanel tablero, List<JLabel> casillas,
            TipoTablero tipoTablero, int numJugadores) {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = tablero;
        this.casillas = casillas;
        this.tipoTablero = tipoTablero;
        this.numJugadores = numJugadores;
    }

    /**
     * Método estático para crear una instancia de TableroBuilder.
     *
     * @param filas Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     * @param tablero Panel que representa el tablero.
     * @param casillas Lista de casillas del tablero.
     * @param tipoTablero Tipo de tablero (ARRIBA, ABAJO, IZQUIERDA, DERECHA,
     * CENTRAL).
     * @param numJugadores Número de jugadores en el juego.
     * @return Instancia de TableroBuilder.
     */
    public static TableroBuilder builderTablero(int filas, int columnas, JPanel tablero,
            List<JLabel> casillas, TipoTablero tipoTablero, int numJugadores) {
        return new TableroBuilder(filas, columnas, tablero, casillas, tipoTablero, numJugadores);
    }

    /**
     * Configura el tamaño del tablero y define el layout del panel.
     */
    private void configurarTamano() {
        int casillaAncho = 60;
        int casillaAlto = 60;
        int panelAncho = 2 * casillaAncho;
        int panelAlto = 7 * casillaAlto;
        tablero.setPreferredSize(new Dimension(panelAncho, panelAlto));
        tablero.setLayout(new GridLayout(filas, columnas, 2, 2));
    }

    /**
     * Construye el tablero según el tipo especificado.
     */
    public void construirTablero() {
        configurarTamano();

        if (tipoTablero == TipoTablero.CENTRAL) {
            construirTableroCentral();
        } else {
            construirTableroExterno();
        }
    }

    /**
     * Construye un tablero externo (ARRIBA, ABAJO, IZQUIERDA, DERECHA).
     */
    private void construirTableroExterno() {
        int filasTemp = filas;
        int columnasTemp = columnas;

        // Ajustar dimensiones para IZQUIERDA y DERECHA
        if (tipoTablero == TipoTablero.IZQUIERDA || tipoTablero == TipoTablero.DERECHA) {
            filasTemp = columnas;
            columnasTemp = filas;
        }

        JLabel[][] casillasTemp = new JLabel[filasTemp][columnasTemp];

        TableroNumeracion num = new TableroNumeracion(numJugadores, tipoTablero);
        int[][] numeracion = num.getNumeracionSegunTipo();

        for (int i = 0; i < filasTemp; i++) {
            for (int j = 0; j < columnasTemp; j++) {
                int numero = numeracion[i][j];
                JLabel casilla = crearCasilla(numero);
                casillasTemp[i][j] = casilla;
                pintarCasillas(casilla, numero);
            }
        }

        for (int i = 0; i < filasTemp; i++) {
            for (int j = 0; j < columnasTemp; j++) {
                tablero.add(casillasTemp[i][j]);
                casillas.add(casillasTemp[i][j]);
            }
        }
    }

    /**
     * Construye el tablero central (CENTRAL).
     */
    private void construirTableroCentral() {
        int[][] numeracionCentral = null;
        if (numJugadores == 2) {
            numeracionCentral = new int[][]{
                {9, 60}, // Primera fila del centro
                {26, 43} // Segunda fila del centro
            };
        } else if (numJugadores == 3) {
            numeracionCentral = new int[][]{
                {11, 72}, // Primera fila del centro
                {32, 53} // Segunda fila del centro
            };
        } else if (numJugadores == 4) {
            numeracionCentral = new int[][]{
                {15, 102}, // Primera fila del centro
                {44, 73} // Segunda fila del centro
            };
        }

        for (int i = 0; i < numeracionCentral.length; i++) {
            for (int j = 0; j < numeracionCentral[i].length; j++) {
                int numero = numeracionCentral[i][j];
                JLabel casilla = crearCasilla(numero);
                tablero.add(casilla);
                casillas.add(casilla);
            }
        }

    }

    /**
     * Crea una casilla (JLabel) con un número específico.
     *
     * @param numero Número asociado a la casilla.
     * @return Casilla configurada.
     */
    private JLabel crearCasilla(int numero) {
        JLabel label = new JLabel();
        label.setText(""); // No se muestra texto
        label.setBorder(new LineBorder(Color.BLACK, 1));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.putClientProperty("numero", numero);
        return label;
    }

    /**
     * Pinta las casillas según su tipo (amarilla o roja).
     *
     * @param casilla Casilla a pintar.
     * @param posicion Posición de la casilla.
     */
    private void pintarCasillas(JLabel casilla, int posicion) {

        if (tipoTablero == TipoTablero.CENTRAL) {
            return;
        }
        CasillaPintar casillas = new CasillaPintar(numJugadores, tipoTablero);

        if (casillas.esCasillaAmarilla(posicion)) {
            casilla.setBackground(Color.YELLOW);
            casillasAmarillasPos.add(posicion);
        }

        if (casillas.esCasillaRoja(posicion)) {
            casilla.setBackground(Color.RED);
        }
    }

    /**
     * Obtiene las posiciones de las casillas amarillas.
     *
     * @return Lista de posiciones de casillas amarillas.
     */
    public List<Integer> getCasillasAmarillas() {
        return new ArrayList<>(casillasAmarillasPos);
    }
}
