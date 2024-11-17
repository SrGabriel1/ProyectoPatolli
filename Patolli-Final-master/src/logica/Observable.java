package logica;

import java.util.ArrayList;

public class Observable {

    private ArrayList<Observer> observadores;

    public Observable() {
        observadores = new ArrayList<>();
    }

    /**
     * Notifica a todos los observadores cuando exista un cambio
     */
    public void notificar(Object arg) {
        for (Observer o : observadores) {
            o.actualizar(this,arg);
        }
    }

    /**
     * Añade un nuevo subscritor u observador para notificarle los cambios
     *
     * @param o el nuevo observador a agregar
     */
    public void agregarObservador(Observer o) {
        observadores.add(o);
    }

    /**
     * Quita un observador de la lista
     *
     * @param o el observador a remover de la lista
     */
    public void quitarObservador(Observer o) {
        observadores.remove(o);
    }

}
