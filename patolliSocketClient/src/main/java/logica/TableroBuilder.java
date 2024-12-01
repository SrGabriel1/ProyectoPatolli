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
    int filasTemp = filas;
    int columnasTemp = columnas;

    // Ajustar dimensiones para IZQUIERDA y DERECHA
    if (tipoTablero == TipoTablero.IZQUIERDA || tipoTablero == TipoTablero.DERECHA) {
        filasTemp = columnas;
        columnasTemp = filas;
    }

    JLabel[][] casillasTemp = new JLabel[filasTemp][columnasTemp];
    int[][] numeracion = getNumeracionSegunTipo();

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

    private int[][] getNumeracionSegunTipo() {
        int[][] numeracion = new int[8][2];

        switch (tipoTablero) {
            case ARRIBA:
                numeracion = new int[][]{
                    {1, 68},
                    {2, 67},
                    {3, 66},
                    {4, 65},
                    {5, 64},
                    {6, 63},
                    {7, 62},
                    {8, 61}
                };
                break;

            case IZQUIERDA:
                numeracion = new int[][]{
                    {17, 16}, // Empieza en 15 y cuenta hacia atrás
                    {15, 14},
                    {13, 12}, // Continúa en orden
                    {11, 10},
                    {18, 19}, // Conecta con el tablero central
                    {20, 21},
                    {22, 23},
                    {24, 25}
                };
                break;

            case ABAJO:
                numeracion = new int[][]{
                    {27, 42}, // Empieza a contar hacia abajo
                    {28, 41},
                    {29, 40},
                    {30, 39},
                    {31, 38},
                    {32, 37},
                    {33, 36},
                    {34, 35}
                };
                break;

            case DERECHA:
                numeracion = new int[][]{
                    {59, 58}, // Continúa hacia la derecha
                    {57, 56},
                    {55, 54},
                    {53, 52},
                    {44, 45},
                    {46, 47},
                    {48, 49},
                    {50, 51}
                };
                break;
        }

        return numeracion;
    }

    private void construirTableroCentral() {
        int[][] numeracionCentral = {
            {9, 60}, // Primera fila del centro
            {26, 43} // Segunda fila del centro
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
                casillaAmarilla = (posicion == 61);
                break;
            case ABAJO:
                casillaAmarilla = (posicion == 27);
                break;
            case IZQUIERDA:
                casillaAmarilla = (posicion == 10);
                break;
            case DERECHA:
                casillaAmarilla = (posicion == 44);
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
                casillaRoja = (posicion == 3 || posicion == 66);
                break;
            case ABAJO:
                casillaRoja = (posicion == 33 || posicion == 36);
                break;
            case IZQUIERDA:
                casillaRoja = (posicion == 15 || posicion == 20);
                break;
            case DERECHA:
                casillaRoja = (posicion == 49 || posicion == 54);
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
