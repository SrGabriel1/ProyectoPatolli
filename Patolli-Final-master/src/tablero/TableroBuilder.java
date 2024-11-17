package tablero;

import java.io.Serializable;

public abstract class TableroBuilder implements Serializable {

    protected Tablero tablero;

    public void crearTablero(int ancho, int alto) {
        tablero = new Tablero(ancho, alto);
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    /**
     * Establece un color default para el tablero
     */
    public abstract void establecerColor();

    /**
     * Establece las casillas que tiene el tablero en cada aspa (no se cuentan
     * las del centro del tablero)
     */
    public abstract void establecerCasillas();

}
