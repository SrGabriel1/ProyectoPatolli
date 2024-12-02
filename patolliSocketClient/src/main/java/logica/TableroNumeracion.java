/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author Gabriel
 */
public class TableroNumeracion {

    private final int numJugadores;
    private final TableroBuilder.TipoTablero tipoTablero;

    public TableroNumeracion(int numJugadores, TableroBuilder.TipoTablero tipoTablero) {
        this.numJugadores = numJugadores;
        this.tipoTablero = tipoTablero;
    }

    public int[][] getNumeracionSegunTipo() {
        switch (numJugadores) {
            case 2:
                return obtenerNumeracionDosJugadores();
            case 3:
                return obtenerNumeracionTresJugadores();
            case 4:
                return obtenerNumeracionCuatroJugadores();
            default:
                throw new IllegalArgumentException("Número de jugadores no soportado: " + numJugadores);
        }
    }

    private int[][] obtenerNumeracionDosJugadores() {
        switch (tipoTablero) {
            case ARRIBA:
                return new int[][]{
                    {1, 68}, {2, 67}, {3, 66}, {4, 65},
                    {5, 64}, {6, 63}, {7, 62}, {8, 61}
                };
            case IZQUIERDA:
                return new int[][]{
                    {17, 16}, {15, 14}, {13, 12}, {11, 10},
                    {18, 19}, {20, 21}, {22, 23}, {24, 25}
                };
            case ABAJO:
                return new int[][]{
                    {27, 42}, {28, 41}, {29, 40}, {30, 39},
                    {31, 38}, {32, 37}, {33, 36}, {34, 35}
                };
            case DERECHA:
                return new int[][]{
                    {59, 58}, {57, 56}, {55, 54}, {53, 52},
                    {44, 45}, {46, 47}, {48, 49}, {50, 51}
                };
            default:
                throw new IllegalArgumentException("Tipo de tablero no soportado.");
        }
    }

    private int[][] obtenerNumeracionTresJugadores() {
        switch (tipoTablero) {
            case ARRIBA:
                return new int[][]{
                    {1, 82}, {2, 81}, {3, 80}, {4, 79},
                    {5, 78}, {6, 77}, {7, 76}, {8, 75},
                    {9, 74}, {10, 73}
                };
            case IZQUIERDA:
                return new int[][]{
                    {21, 20}, {19, 18}, {17, 16}, {15, 14},
                    {13, 12}, {22, 23}, {24, 25}, {26, 27},
                    {28, 29}, {30, 31}
                };
            case ABAJO:
                return new int[][]{
                    {33, 52}, {34, 51}, {35, 50}, {36, 49},
                    {37, 48}, {38, 47}, {39, 46}, {40, 45},
                    {41, 44}, {42, 43}
                };
            case DERECHA:
                return new int[][]{
                    {71, 70}, {69, 68}, {67, 66}, {65, 64},
                    {44, 45}, {54, 55}, {56, 57}, {58, 59},
                    {60, 61}, {62, 63}
                };
            default:
                throw new IllegalArgumentException("Tipo de tablero no soportado.");
        }
    }

    private int[][] obtenerNumeracionCuatroJugadores() {
        switch (tipoTablero) {
            case ARRIBA:
                return new int[][]{
                    {1, 116}, {2, 115}, {3, 114}, {4, 113},
                    {5, 112}, {6, 111}, {7, 110}, {8, 109},
                    {9, 108}, {10, 107}, {11, 106}, {12, 105},
                    {13, 104}, {14, 103}
                };
            case IZQUIERDA:
                return new int[][]{
                    {29, 28}, {27, 26}, {25, 24}, {23, 22},
                    {21, 20}, {19, 18}, {17, 16}, {30, 31},
                    {32, 33}, {34, 35}, {36, 37}, {38, 39},
                    {40, 41}, {42, 43}
                };
            case ABAJO:
                return new int[][]{
                    {45, 72}, {46, 71}, {47, 70}, {48, 69},
                    {49, 68}, {50, 67}, {51, 66}, {52, 65},
                    {53, 64}, {54, 63}, {55, 62}, {56, 61},
                    {57, 60}, {58, 59}
                };
            case DERECHA:
                return new int[][]{
                    {101, 100}, {99, 98}, {97, 96}, {95, 94},
                    {93, 92}, {91, 90}, {89, 88}, {74, 75},
                    {76, 77}, {78, 79}, {80, 81}, {82, 83},
                    {84, 85}, {86, 87}
                };
            default:
                throw new IllegalArgumentException("Tipo de tablero no soportado.");
        }
    }
}
