/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
            List<JLabel> casillas, TipoTablero tipoTablero,
            int numJugadores) {
        return new TableroBuilder(filas, columnas, tablero, casillas, tipoTablero, numJugadores);
    }

    private void configurarTamano() {
        int casillaAncho = 100;  // Cambiar este valor si es necesario
        int casillaAlto = 100;   // Cambiar este valor si es necesario

        int panelAncho = columnas * casillaAncho;
        int panelAlto = filas * casillaAlto;
        tablero.setPreferredSize(new Dimension(panelAncho, panelAlto));
        
        // Utiliza GridLayout con espaciado adecuado
        tablero.setLayout(new GridLayout(filas, columnas, 0, 0)); // 0px de espaciado
    }

    public void construirTablero() {
        configurarTamano();
        for (int i = 1; i <= filas * columnas; i++) {
            JLabel casilla = crearCasilla();
            pintarCasillas(casilla, i);
            tablero.add(casilla);
            casillas.add(casilla);
        }
    }

    private JLabel crearCasilla() {
        JLabel label = new JLabel("");
        label.setBorder(new LineBorder(Color.BLACK, 1));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setPreferredSize(new Dimension(100, 100));
        return label;
    }

    private void pintarCasillas(JLabel casilla, int posicion) {
        if (tipoTablero == TipoTablero.CENTRAL) {
            return;
        }

        pintarCasillaRoja(casilla, posicion);
        pintarCasillaAmarilla(casilla, posicion);
    }

    private void pintarCasillaRoja(JLabel casilla, int posicion) {
        if (tipoTablero == TipoTablero.CENTRAL) {
            return;
        }

        boolean casillaRoja = false;

        switch (numJugadores) {
            case 2:
                switch (tipoTablero) {
                    case ARRIBA:
                        casillaRoja = (posicion == 5 || posicion == 6);
                        break;
                    case ABAJO:
                        casillaRoja = (posicion == 11 || posicion == 12);
                        break;
                    case IZQUIERDA:
                        casillaRoja = (posicion == 3 || posicion == 11);
                        break;
                    case DERECHA:
                        casillaRoja = (posicion == 6 || posicion == 14);
                        break;
                }
                break;
            case 3:
                switch (tipoTablero) {
                    case ARRIBA:
                        casillaRoja = (posicion == 5 || posicion == 6);
                        break;
                    case ABAJO:
                        casillaRoja = (posicion == 15 || posicion == 16);
                        break;
                    case IZQUIERDA:
                        casillaRoja = (posicion == 3 || posicion == 13);
                        break;
                    case DERECHA:
                        casillaRoja = (posicion == 8 || posicion == 18);
                        break;
                }
                break;
            case 4:
                switch (tipoTablero) {
                    case ARRIBA:
                        casillaRoja = (posicion == 5 || posicion == 6);
                        break;
                    case ABAJO:
                        casillaRoja = (posicion == 23 || posicion == 24);
                        break;
                    case IZQUIERDA:
                        casillaRoja = (posicion == 3 || posicion == 17);
                        break;
                    case DERECHA:
                        casillaRoja = (posicion == 12 || posicion == 26);
                        break;
                }
                break;
        }
        if (casillaRoja) {
            casilla.setBackground(Color.RED);
        }
    }

    private void pintarCasillaAmarilla(JLabel casilla, int posicion) {
        if (tipoTablero == TipoTablero.CENTRAL) {
            return;
        }

        boolean casillaAmarilla = false;

        switch (numJugadores) {
            case 2:
                switch (tipoTablero) {
                    case ARRIBA:
                        casillaAmarilla = (posicion == 16);
                        break;
                    case ABAJO:
                        casillaAmarilla = (posicion == 1);
                        break;
                    case IZQUIERDA:
                        casillaAmarilla = (posicion == 8);
                        break;
                    case DERECHA:
                        casillaAmarilla = (posicion == 9);
                        break;
                }
                break;
            case 3:
                switch (tipoTablero) {
                    case ARRIBA:
                        casillaAmarilla = (posicion == 20);
                        break;
                    case ABAJO:
                        casillaAmarilla = (posicion == 1);
                        break;
                    case IZQUIERDA:
                        casillaAmarilla = (posicion == 10);
                        break;
                    case DERECHA:
                        casillaAmarilla = (posicion == 11);
                        break;
                }
                break;
            case 4:
                switch (tipoTablero) {
                    case ARRIBA:
                        casillaAmarilla = (posicion == 28);
                        break;
                    case ABAJO:
                        casillaAmarilla = (posicion == 1);
                        break;
                    case IZQUIERDA:
                        casillaAmarilla = (posicion == 14);
                        break;
                    case DERECHA:
                        casillaAmarilla = (posicion == 15);
                        break;
                }
                break;
        }

        if (casillaAmarilla) {
            casilla.setBackground(Color.YELLOW);
        }
    }
}
