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
        int[][] numeracion = null;
        if (numJugadores == 2) {
            numeracion = new int[8][2];
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
        } else if (numJugadores == 3) {
            numeracion = new int[10][2];
            switch (tipoTablero) {
                case ARRIBA:
                    numeracion = new int[][]{
                        {1, 82},
                        {2, 81},
                        {3, 80},
                        {4, 79},
                        {5, 78},
                        {6, 77},
                        {7, 76},
                        {8, 75},
                        {9, 74},
                        {10, 73}
                    };
                    break;

                case IZQUIERDA:
                    numeracion = new int[][]{
                        {21, 20}, // Empieza en 15 y cuenta hacia atrás
                        {19, 18},
                        {17, 16}, // Continúa en orden
                        {15, 14},
                        {13, 12}, // Conecta con el tablero central
                        {22, 23},
                        {24, 25},
                        {26, 27},
                        {28, 29},
                        {30, 31}
                    };
                    break;

                case ABAJO:
                    numeracion = new int[][]{
                        {33, 52}, // Empieza a contar hacia abajo
                        {34, 51},
                        {35, 50},
                        {36, 49},
                        {37, 48},
                        {38, 47},
                        {39, 46},
                        {40, 45},
                        {41, 44},
                        {42, 43}
                    };
                    break;

                case DERECHA:
                    numeracion = new int[][]{
                        {71, 70}, // Continúa hacia la derecha
                        {69, 68},
                        {67, 66},
                        {65, 64},
                        {44, 45},
                        {54, 55},
                        {56, 57},
                        {58, 59},
                        {60, 61},
                        {62, 63}
                    };
                    break;
            }
        } else if (numJugadores == 4) {
            numeracion = new int[14][2];
            switch (tipoTablero) {
                case ARRIBA:
                    numeracion = new int[][]{
                        {1, 116},
                        {2, 115},
                        {3, 114},
                        {4, 113},
                        {5, 112},
                        {6, 111},
                        {7, 110},
                        {8, 109},
                        {9, 108},
                        {10, 107},
                        {11, 106},
                        {12, 105},
                        {13, 104},
                        {14, 103}
                    };
                    break;

                case IZQUIERDA:
                    numeracion = new int[][]{
                        {29, 28}, // Empieza en 15 y cuenta hacia atrás
                        {27, 26},
                        {25, 24}, // Continúa en orden
                        {23, 22},
                        {21, 20}, // Conecta con el tablero central
                        {19, 18},
                        {17, 16},
                        {30, 31},
                        {32, 33},
                        {34, 35},
                        {36, 37},
                        {38, 39},
                        {40, 41},
                        {42, 43}
                    };
                    break;

                case ABAJO:
                    numeracion = new int[][]{
                        {45, 72}, // Empieza a contar hacia abajo
                        {46, 71},
                        {47, 70},
                        {48, 69},
                        {49, 68},
                        {50, 67},
                        {51, 66},
                        {52, 65},
                        {53, 64},
                        {54, 63},
                        {55, 62},
                        {56, 61},
                        {57, 60},
                        {58, 59}

                    };
                    break;

                case DERECHA:
                    numeracion = new int[][]{
                        {101, 100}, // Continúa hacia la derecha
                        {99, 98},
                        {97, 96},
                        {95, 94},
                        {93, 92},
                        {91, 90},
                        {89, 88},
                        {74, 75},
                        {76, 77},
                        {78, 79},
                        {80, 81},
                        {82, 83},
                        {84, 85},
                        {86, 87}
                    };
                    break;
            }
        }

        return numeracion;
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

        if (numJugadores == 2) {
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
        } else if (numJugadores == 3) {
            // Casillas amarillas según la imagen
            boolean casillaAmarilla = false;
            switch (tipoTablero) {
                case ARRIBA:
                    casillaAmarilla = (posicion == 73);
                    break;
                case ABAJO:
                    casillaAmarilla = (posicion == 33);
                    break;
                case IZQUIERDA:
                    casillaAmarilla = (posicion == 12);
                    break;
                case DERECHA:
                    casillaAmarilla = (posicion == 54);
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
                    casillaRoja = (posicion == 3 || posicion == 80);
                    break;
                case ABAJO:
                    casillaRoja = (posicion == 40 || posicion == 45);
                    break;
                case IZQUIERDA:
                    casillaRoja = (posicion == 24 || posicion == 19);
                    break;
                case DERECHA:
                    casillaRoja = (posicion == 64 || posicion == 61);
                    break;
            }

            if (casillaRoja) {
                casilla.setBackground(Color.RED);
            }

        } else if (numJugadores == 4) {
            // Casillas amarillas según la imagen
            boolean casillaAmarilla = false;
            switch (tipoTablero) {
                case ARRIBA:
                    casillaAmarilla = (posicion == 103);
                    break;
                case ABAJO:
                    casillaAmarilla = (posicion == 45);
                    break;
                case IZQUIERDA:
                    casillaAmarilla = (posicion == 16);
                    break;
                case DERECHA:
                    casillaAmarilla = (posicion == 74);
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
                    casillaRoja = (posicion == 3 || posicion == 114);
                    break;
                case ABAJO:
                    casillaRoja = (posicion == 56 || posicion == 61);
                    break;
                case IZQUIERDA:
                    casillaRoja = (posicion == 27 || posicion == 32);
                    break;
                case DERECHA:
                    casillaRoja = (posicion == 90 || posicion == 85);
                    break;
            }

            if (casillaRoja) {
                casilla.setBackground(Color.RED);
            }
        }
    }

    public List<Integer> getCasillasAmarillas() {
        return new ArrayList<>(casillasAmarillasPos);
    }
}
