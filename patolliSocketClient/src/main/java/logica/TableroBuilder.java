/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

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

    public enum TipoTablero {
        ARRIBA, ABAJO, IZQUIERDA, DERECHA, CENTRAL
    }

    private TableroBuilder(int filas, int columnas, JPanel tablero, List<JLabel> casillas,
            TipoTablero tipoTablero, int numJugadores) {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = tablero;
        this.casillas = casillas;
        this.tipoTablero = tipoTablero;
        this.numJugadores = numJugadores;
    }

    public static TableroBuilder builderTablero(int filas, int columnas, JPanel tablero,
            List<JLabel> casillas, TipoTablero tipoTablero, int numJugadores) {
        return new TableroBuilder(filas, columnas, tablero, casillas, tipoTablero, numJugadores);
    }

    private void configurarTamano() {
        int casillaAncho = 60;  // Reducido de 100 a 60
        int casillaAlto = 60;   // Reducido de 100 a 60
        int panelAncho = 2 * casillaAncho;
        int panelAlto = 7 * casillaAlto;
        tablero.setPreferredSize(new Dimension(panelAncho, panelAlto));
        tablero.setLayout(new GridLayout(filas, columnas, 2, 2)); // Añadido gap de 2 pixels
    }

    public void construirTablero() {
        configurarTamano();

        if (tipoTablero == TipoTablero.CENTRAL) {
            construirTableroCentral();
        } else {
            construirTableroExterno();
        }
    }

    private void construirTableroExterno() {
        JLabel[][] casillasTemp = new JLabel[7][2];

        // Determinar el rango de números según el tipo de tablero
        int[][] numeracion = getNumeracionSegunTipo();

        // Crear y numerar las casillas
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                int numero = numeracion[i][j];
                JLabel casilla = crearCasilla(numero);
                casillasTemp[i][j] = casilla;
                pintarCasillas(casilla, numero);
            }
        }

        // Agregar las casillas al tablero
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 2; j++) {
                tablero.add(casillasTemp[i][j]);
                casillas.add(casillasTemp[i][j]);
                
            }
        }
    }

    private int[][] getNumeracionSegunTipo() {
        int[][] numeracion = new int[7][2];

        switch (tipoTablero) {
            case ARRIBA:
                numeracion = new int[][]{
                    {1, 60},
                    {2, 59},
                    {3, 58},
                    {4, 57},
                    {5, 56},
                    {6, 55},
                    {7, 54}
                };
                break;

            case IZQUIERDA:
                numeracion = new int[][]{
                    {15, 14}, // Empieza en 15 y cuenta hacia atrás
                    {13, 12},
                    {11, 10}, // Continúa en orden
                    {9, 16},
                    {17, 18}, // Conecta con el tablero central
                    {19, 20},
                    {21, 22}
                };
                break;

            case ABAJO:
                numeracion = new int[][]{
                    {24, 37}, // Empieza a contar hacia abajo
                    {25, 36},
                    {26, 35},
                    {27, 34},
                    {28, 33},
                    {29, 32},
                    {30, 31}
                };
                break;

            case DERECHA:
                numeracion = new int[][]{
                    {52, 51}, // Continúa hacia la derecha
                    {50, 49},
                    {48, 47},
                    {46, 39},
                    {40, 41},
                    {42, 43},
                    {44, 45}
                };
                break;
        }

        return numeracion;
    }

    private void construirTableroCentral() {
        int[][] numeracionCentral = {
            {8, 53}, // Primera fila del centro
            {23, 38} // Segunda fila del centro
        };

        for (int i = 0; i < numeracionCentral.length; i++) {
            for (int j = 0; j < numeracionCentral[i].length; j++) {
                int numero = numeracionCentral[i][j]; // Obtener número desde la matriz
                JLabel casilla = crearCasilla(numero); // Crear la casilla
                tablero.add(casilla); // Agregar al tablero
                casillas.add(casilla); // Guardar referencia
            }
        }

    }

    private JLabel crearCasilla(int numero) {
        JLabel label = new JLabel(numero > 0 ? String.valueOf(numero) : "");
        label.setBorder(new LineBorder(Color.BLACK, 1));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        return label;
    }

    private void pintarCasillas(JLabel casilla, int posicion) {
        if (tipoTablero == TipoTablero.CENTRAL) {
            return;
        }

        // Casillas amarillas según la imagen
        boolean casillaAmarilla = false;
        switch (tipoTablero) {
            case ARRIBA:
                casillaAmarilla = (posicion == 54);
                break;
            case ABAJO:
                casillaAmarilla = (posicion == 24);
                break;
            case IZQUIERDA:
                casillaAmarilla = (posicion == 9);
                break;
            case DERECHA:
                casillaAmarilla = (posicion == 52);
                break;
        }

        if (casillaAmarilla) {
            casilla.setBackground(Color.YELLOW);
            casillasAmarillasPos.add(posicion);
        }

        // Casillas rojas según la imagen
        boolean casillaRoja = false;
        switch (tipoTablero) {
            case ARRIBA:
                casillaRoja = (posicion == 3 || posicion == 58);
                break;
            case ABAJO:
                casillaRoja = (posicion == 28 || posicion == 33);
                break;
            case IZQUIERDA:
                casillaRoja = (posicion == 13 || posicion == 18);
                break;
            case DERECHA:
                casillaRoja = (posicion == 43 || posicion == 48);
                break;
        }

        if (casillaRoja) {
            casilla.setBackground(Color.RED);
        }
    }

    public List<Integer> getCasillasAmarillas() {
        return new ArrayList<>(casillasAmarillasPos);
    }
}
