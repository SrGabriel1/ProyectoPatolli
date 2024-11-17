package logica;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import logica.apuestas.Apuestas;
import tablero.*;
import utileria.Dado;
import utileria.Ficha;

public class Reglamento implements Observer, Serializable {

    private ArrayList<Jugador> jugadores;
    private Tablero tablero;
    private Dado dado;
    private int turno, ganador;
    private Apuestas manejoApuestas;
    private boolean fin;
    private float fondoInicial;
    private int[] resultados;

    public Reglamento(int tipoTablero, int alto, int ancho) {
        this.dado = new Dado();
        this.jugadores = new ArrayList<>();
        this.fin = false;
        this.ganador = -1;
        establecerTablero(tipoTablero, alto, ancho);
    }

    /**
     * Establece el tamaño del tablero dependiendo del tipo dado por parametro
     *
     * @param tipo el tipo de tablero deseado donde: 0 = chico, 1 = mediano, 2 =
     * grande, 3 = extra grande.
     */
    private void establecerTablero(int tipo, int alto, int ancho) {
        DirectorTablero dt = new DirectorTablero();
        TableroBuilder tipoTablero;

        switch (tipo) {
            case 0:
                tipoTablero = new TableroChicoBuilder();
                dt.setTableroBuilder(tipoTablero);
                dt.construirTablero(ancho, alto);
                tablero = dt.getTablero();
                break;
            case 1:
                tipoTablero = new TableroMedianoBuilder();
                dt.setTableroBuilder(tipoTablero);
                dt.construirTablero(ancho, alto);
                tablero = dt.getTablero();
                break;
            case 2:
                tipoTablero = new TableroGrandeBuilder();
                dt.setTableroBuilder(tipoTablero);
                dt.construirTablero(ancho, alto);
                tablero = dt.getTablero();
                break;
            case 3:
                tipoTablero = new TableroExtraGrandeBuilder();
                dt.setTableroBuilder(tipoTablero);
                dt.construirTablero(ancho, alto);
                tablero = dt.getTablero();
                break;
        }
    }

    //Recibe la info del jugador respecto a su economía
    @Override
    public void actualizar(Observable o, Object arg) {
        Jugador jugador = (Jugador) o;
        if(jugador.getDinero()<=0){
            jugador.setEliminado(true);
            System.out.println("Jugador eliminado");
            this.jugadores.set(turno, jugador);
        }
    }

    /**
     * Agrega un nuevo jugador a la lista
     *
     * @param jugador el jugador a añadir
     */
    public void agregarJugador(Jugador jugador) {
        if (this.jugadores.size() >= 4) {
            JOptionPane.showMessageDialog(null, "De momento no se aceptan más jugadores!", "Error!", 0);
            return;
        }

        jugador.agregarObservador(this);
        this.jugadores.add(jugador);

    }

    /**
     * Este método actualiza los cambios dependiendo de los resultados arrojados
     *
     * @param resultados los resultados de lanzamiento de las cañas donde la
     * posicion 0 deben ser los puntos y la posición 1 las lisas
     * @param numFicha el número de la ficha a mover si se recibe un -1 se elige
     * una ficha random
     */
    public void actualizarCambios(int[] resultados, int numFicha) {
        this.resultados = resultados;
        
        //Determinación de ficha a mover y turnos
        Ficha ficha = null;
        boolean esPrimera = false;
        boolean esReiniciada = false;
        
        if (resultados[0] != 0) {
            //Si el numFicha es -1 es que el jugador se desconectó, se elige la 
            //primera ficha disponible para mover
            if (numFicha == -1) {
                for (int i = 0; i < jugadores.get(turno).getFichas().size(); i++) {
                    ficha = jugadores.get(turno).getFichas().get(i);
                    if (!ficha.isComida() && !ficha.isMetaFicha()) {
                        break;
                    }
                }
            } else { //si no es -1 se elige la ficha dada
                ficha = jugadores.get(turno).getFichas().get(numFicha);
            }

            //En cualquier caso no se mueven fichas que ya hayan sido comidas o
            //que hayan llegado a la meta
            if (ficha != null) {
                if (!ficha.isComida() && !ficha.isMetaFicha()) {
                    //Se remueve la ficha de esa casilla
                    if (ficha.getPosicion() >= 0) {
                        tablero.getPosiciones()[ficha.getPosicion()].quitarFicha(ficha.getNumFicha());
                    }
                    
                    int avance;
                    //Si se sacaron 5 puntos se avanza 10 casillas
                    if (resultados[0] == 5) {
                        avance = 10;
                    } else {//Caso contrario se avanzan los puntos obtenidos
                        avance = resultados[0];
                    }
                    //si la posición es menor a cero es que no ha sido agregada
                    //se le resetea la posición dependiendo de que jugador sea
                    if (ficha.getPosicion() < 0) {
                        for (int i = 0; i < tablero.getPosiciones().length; i++) {
                            if (tablero.getPosiciones()[i].getCasillaJugador() == turno) {
                                ficha.setPosicion(i - 1);
                                esPrimera = true;
                                break;
                            }
                        }
                    }

                    //Se valida que avance las casillas correctas en caso que no haya
                    //más posiciones en el tablero
                    if (ficha.getPosicion() + avance > tablero.getPosiciones().length - 1) {
                        ficha.setPosicion(avance - (tablero.getPosiciones().length - 1 - ficha.getPosicion()) - 1);
                    } else {
                        ficha.setPosicion(ficha.getPosicion() + avance);
                    }

                    //Aquí van las validaciones de movimiento
                    //Aplicando reglas dependiendo si cae en casillas especiales
                    //Si cae en una casilla ocupada por otro jugador
                    if (tablero.getPosiciones()[ficha.getPosicion()].isHayFicha() && tablero.getPosiciones()[ficha.getPosicion()].getTurnoFicha() != turno) {
                        ArrayList<Integer> fichasAct = tablero.getPosiciones()[ficha.getPosicion()].getNumFicha();
                        int numJug = tablero.getPosiciones()[ficha.getPosicion()].getTurnoFicha();
                        //Si es una de las caillas centrales la ficha que estaba es eliminada
                        if (tablero.getPosiciones()[ficha.getPosicion()].isMedio()) {
                            for (int i = 0; i < jugadores.get(numJug).getFichas().size(); i++) {
                                for (int j=0; j < fichasAct.size();j++) {
                                    if (jugadores.get(numJug).getFichas().get(i).getNumFicha() == fichasAct.get(j)) {
                                        jugadores.get(numJug).getFichas().get(i).setComida(true);
                                        tablero.getPosiciones()[jugadores.get(numJug).getFichas().get(i).getPosicion()].quitarFicha(fichasAct.get(j));
                                    }
                                }
                            }

                        } else {//Sino es una casilla central la ficha que estaba se devuelve a su inicio
                            for (int i = 0; i < tablero.getPosiciones().length; i++) {
                                if (tablero.getPosiciones()[i].getCasillaJugador() == numJug) {
                                    for (int j = 0; j < jugadores.get(numJug).getFichas().size(); j++) {
                                        for (int k = 0; k < fichasAct.size(); k++) {
                                            if (jugadores.get(numJug).getFichas().get(j).getNumFicha() == fichasAct.get(k)) {
                                                tablero.getPosiciones()[jugadores.get(numJug).getFichas().get(j).getPosicion()].quitarFicha(fichasAct.get(k));
                                                jugadores.get(numJug).getFichas().get(j).setPosicion(i);
                                                esReiniciada = true;
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    //Si cae en una casilla con triangulo paga una doble apuesta
                    if (tablero.getPosiciones()[ficha.getPosicion()].isTriangulo()) {
                        pagarApuesta();
                        pagarApuesta();
                    }

                    //Avanza las posiciones pertinentes
                    for (int i = 0; i < jugadores.get(turno).getFichas().size(); i++) {
                        if (ficha.equals(jugadores.get(turno).getFichas().get(i))) {
                            jugadores.get(turno).getFichas().get(i).setPosicion(ficha.getPosicion());
                            //determina si la ficha llegó a la meta
                            if(tablero.getPosiciones()[ficha.getPosicion()].getCasillaJugador()==turno && !esPrimera &&!esReiniciada){
                                jugadores.get(turno).getFichas().get(i).setMetaFicha(true);
                                break;
                            }
                            //aquí pones una nueva ficha en esa casilla
                            tablero.getPosiciones()[ficha.getPosicion()].ponerFicha(i, turno);
                            break;
                        }
                    }

                    //Si cae en una esquina, juega doble turno
                    if (tablero.getPosiciones()[ficha.getPosicion()].isEsquina()) {
                        turno--;
                    }

                }
            }
        } else {//Se paga una apuesta cuando la tirada no es exitosa
            pagarApuesta();
        }
        
        //Cuando un jugador no tenga más fichas disponibles lo eliminas
        for (int i = 0; i < jugadores.size(); i++) {
            if(!jugadores.get(i).fichasDisponibles()){
                jugadores.get(i).setEliminado(true);
            }
        }
        
        //Si todos los jugadores han sido eliminados se determina
        boolean todosEliminados = true;
        for (int i = 0; i < jugadores.size(); i++) {
            if(jugadores.get(i).isEliminado()==false){
                System.out.println("No han sido todos eliminados");
                todosEliminados = false;
                break;
            }
        }
        
        //Si un jugador ya metió todas sus fichas da fin al juego
        int mayor = 0;
        for (int i = 0; i < jugadores.size(); i++) {
            if(jugadores.get(i).todasFichasMeta()){
                fin = true;
                ganador = i;
                System.out.println("Un jugador metió todas sus fichas");
                break;
            }
        }
        
        //Si todos fueron eliminados determina quien metió mas fichas
        if(todosEliminados){
            System.out.println("Todos son eliminados");
            for (int i = 0; i < jugadores.size(); i++) {
                if(jugadores.get(i).getFichasMeta()>mayor){
                    mayor = jugadores.get(i).getFichasMeta();
                    ganador = i;
                }
            }
            System.out.println("El que metió más fichas "+ganador);
            fin = true;
        }
        
        //Avanza al siguiente turno
        turno++;//Si se llega el turno del ultimo jugador, se reinician turnos
        if (turno > jugadores.size() - 1) {
            turno = 0;
        }

        //Enviando al tablero las fichas que debe dibujar
        //Solo se dibujam las fichas que estén dentro del tablero
        ArrayList<Ficha> fichas = new ArrayList<>();
        for (Jugador player : jugadores) {
            for (Ficha item : player.getFichas()) {
                if (!item.isComida() && !item.isMetaFicha() && item.getPosicion() >= 0) {
                    fichas.add(item);
                }
            }
        }
        //Se repinta el tablero con la info actualizada
        this.tablero.setFichas(fichas);
        this.tablero.repaint();
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Dado getDado() {
        return dado;
    }

    public int getTurno() {
        return turno;
    }

    public float getFondoInicial() {
        return fondoInicial;
    }

    public void setFondoInicial(float fondoInicial, float porcentaje) {
        this.fondoInicial = fondoInicial;
        this.manejoApuestas = new Apuestas(this, fondoInicial);
        this.manejoApuestas.establecerPorcentajeApuesta(porcentaje);
    }

    public Apuestas getManejoApuestas() {
        return manejoApuestas;
    }

    public int[] getResultados() {
        return resultados;
    }

    private void pagarApuesta() {
        manejoApuestas.setJuego(this);
        manejoApuestas.pagarApuesta(jugadores.get(turno));
        for (int i = 0; i < jugadores.size(); i++) {
            jugadores.set(i, manejoApuestas.getJuego().getJugadores().get(i));
        }
    }

    public boolean isFin() {
        return fin;
    }

    public int getGanador() {
        return ganador;
    }
    
}
