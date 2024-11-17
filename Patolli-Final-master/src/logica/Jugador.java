package logica;

import java.io.Serializable;
import java.util.ArrayList;
import utileria.Ficha;

public class Jugador extends Observable implements Serializable {

    private ArrayList<Ficha> fichas;
    private float dinero;
    private String nombre;
    private int numJugador;
    private boolean eliminado;

    public Jugador(float dinero, String nombre, int numJugador, ArrayList<Ficha> fichas) {
        this.dinero = dinero;
        this.nombre = nombre;
        this.numJugador = numJugador;
        this.fichas = fichas;
        this.eliminado = false;
    }

    public float getDinero() {
        return dinero;
    }

    public ArrayList<Ficha> getFichas() {
        return fichas;
    }

    public void setFichas(ArrayList<Ficha> fichas) {
        this.fichas = fichas;
    }

    public void setDinero(float dinero) {
        this.dinero = dinero;
        this.notificar(dinero);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumJugador(int numJugador) {
        this.numJugador = numJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumJugador() {
        return numJugador;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public boolean todasFichasMeta() {
        boolean result = true;
        for (int i = 0; i < fichas.size(); i++) {
            if (!fichas.get(i).isMetaFicha()) {
                result = false;
                break;
            }
        }

        return result;
    }

    public boolean fichasDisponibles() {
        boolean result = false;
        for (int i = 0; i < fichas.size(); i++) {
            if (!fichas.get(i).isMetaFicha() || !fichas.get(i).isComida()) {
                result = true;
                break;
            }
        }
        return result;
    }

    public int getFichasMeta() {
        int result = 0;
        for (int i = 0; i < fichas.size(); i++) {
            if(fichas.get(i).isMetaFicha()){
                result++;
            }
        }
        
        return result;
    }

}
