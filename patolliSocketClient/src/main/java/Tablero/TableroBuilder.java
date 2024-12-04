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

        TableroNumeracion num = new TableroNumeracion(numJugadores, tipoTablero);
        int[][] numeracion = num.getNumeracionSegunTipo();

        for (int i = 0; i < filasTemp; i++) {
            for (int j = 0; j < columnasTemp; j++) {
                int numero = numeracion[i][j];
                JLabel casilla = crearCasilla();
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
                int numero = numeracionCentral[i][j]; // Obtener número desde la matriz
                JLabel casilla = crearCasilla(); // Crear la casilla
                tablero.add(casilla); // Agregar al tablero
                casillas.add(casilla); // Guardar referencia
            }
        }

    }

    private JLabel crearCasilla() {
        JLabel label = new JLabel();
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
        CasillaPintar casillas = new CasillaPintar(numJugadores, tipoTablero);

        if (casillas.esCasillaAmarilla(posicion)) {
            casilla.setBackground(Color.YELLOW);
            casillasAmarillasPos.add(posicion);
        }

        if (casillas.esCasillaRoja(posicion)) {
            casilla.setBackground(Color.RED);
        }
    }
    public List<Integer> getCasillasAmarillas() {
        return new ArrayList<>(casillasAmarillasPos);
    }
}