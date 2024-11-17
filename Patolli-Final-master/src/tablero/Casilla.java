package tablero;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class Casilla implements Serializable {

    private Point p;
    private boolean hayFicha;//indica si hay una ficha en esa casilla
    private boolean medio;//indica si es una casilla del medio
    private boolean esquina;//indica si es una esquina
    private boolean triangulo;//indica si es una casilla con triangulo
    private boolean inicio;//indica si es una casilla de inicio para jugador
    //si es una casilla donde inicia el jugador se le indica que número de 
    //jugador es su inicio, sino su valor es -1;
    private int casillaJugador;
    //si hay una ficha en la casilla indica el numero del jugador que está en esa casilla
    //sino hay jugador su valor es -1
    private int turnoFicha;
    //si hay una ficha en la casilla indica que ficha está en esa casilla
    //si no hay ficha su valor es -1;
    private ArrayList<Integer> numFicha;

    public Casilla(Point p) {
        this.p = p;
        this.hayFicha = false;
        this.medio = false;
        this.esquina = false;
        this.triangulo = false;
        this.inicio = false;
        this.casillaJugador = -1;
        this.turnoFicha = -1;
        this.numFicha = new ArrayList<>();
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public boolean isHayFicha() {
        return hayFicha;
    }

    public void setHayFicha(boolean hayFicha) {
        this.hayFicha = hayFicha;
    }

    public boolean isMedio() {
        return medio;
    }

    public void setMedio(boolean medio) {
        this.medio = medio;
    }

    public boolean isEsquina() {
        return esquina;
    }

    public void setEsquina(boolean esquina) {
        this.esquina = esquina;
    }

    public boolean isTriangulo() {
        return triangulo;
    }

    public void setTriangulo(boolean triangulo) {
        this.triangulo = triangulo;
    }

    public boolean isInicio() {
        return inicio;
    }

    public void setInicio(boolean inicio) {
        this.inicio = inicio;
    }

    public int getCasillaJugador() {
        return casillaJugador;
    }

    public void setCasillaJugador(int casillaJugador) {
        this.casillaJugador = casillaJugador;
    }

    public int getTurnoFicha() {
        return turnoFicha;
    }

    public void setTurnoFicha(int turnoFicha) {
        this.turnoFicha = turnoFicha;
    }

    public ArrayList getNumFicha() {
        return numFicha;
    }

    public void ponerFicha(int numFicha, int turnoFicha) {
        this.hayFicha = true;
        this.numFicha.add(numFicha);
        this.setTurnoFicha(turnoFicha);
    }

    public void quitarFicha(int numFichaRemove) {
        if (!this.numFicha.isEmpty()) {
            for (int i = 0; i < numFicha.size(); i++) {
                if(numFicha.get(i).equals(numFichaRemove)){
                    numFicha.remove(i);
                }
            }
            if (numFicha.isEmpty()) {
                this.setTurnoFicha(-1);
                this.hayFicha = false;
            }
        }
    }

}
