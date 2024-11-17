package tablero;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Arc2D;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JPanel;
import utileria.Ficha;

public class Tablero extends JPanel implements Serializable {

    private int ancho, alto, casillasAspa, w, h, gap;
    private Color color;
    private Casilla[] posiciones;
    private ArrayList<Ficha> fichas;

    public Tablero(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        w = 20;
        h = 20;
        gap = 5;
        this.setBounds(0, 0, ancho, alto);
        this.setBackground(new Color(37, 36, 36));
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getCasillasAspa() {
        return casillasAspa;
    }

    public void setCasillasAspa(int casillasAspa) {
        this.casillasAspa = casillasAspa;
        this.posiciones = new Casilla[casillasAspa*4+4];
        generarPosiciones();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Casilla[] getPosiciones() {
        return posiciones;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getGap() {
        return gap;
    }

    /**
     * Dibuja las fichas de todos los jugadores en el tablero
     *
     * @param g2d grafico para pintar las fichas
     */
    public void dibujarFichas(Graphics2D g2d) {
        

        //pinta las fichas con su respectivo color y en su posición
        if (this.fichas != null) {
            for (Ficha ficha : fichas) {
                g2d.setColor(ficha.getColor());
                //solo pinta fichas que no hayan sido comidas ni hayan llegado a la meta
                //y si su posición es -2 se determina que la ficha aún no ha sido agregada
                if (!ficha.isComida() && !ficha.isMetaFicha() && ficha.getPosicion() != -2) {
                    Point pos = this.posiciones[ficha.getPosicion()].getP();
                    g2d.fillOval((int) pos.getX(), (int) pos.getY(), w, h);
                }
            }
        }
        g2d.setColor(color);
    }

    /**
     * Dibuja la imagen del tablero definido
     *
     * @param g2d grafico para pintar los elementos
     */
    private void dibujarTablero(Graphics2D g2d) {
        //indica cuantas casillas se deben pintar (todas escepto las esquinas)
        //las esquinas se pintan sin ser afectadas por este calculo
        int casillas = (int) (((casillasAspa / 2) - 1) * 4 + 4);

        //El inicio para las casillas en vertical
        int inicioV = (alto / 2) - (((casillas - 4) / 4) * h + gap * ((casillas - 4) / 4));
        //El inicio para las casillas en horizontal
        int inicioH = (ancho / 2) - (((casillas - 4) / 4) * w + gap * ((casillas - 4) / 4)) - w + gap;

        //Dibujar ambas aspas
        dibujarAspaHorizontal(g2d, w, h, gap, inicioH, casillas);
        dibujarAspaVertical(g2d, w, h, gap, inicioV - gap - h, casillas);
        //Dibuja todas las fichas
        dibujarFichas(g2d);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        this.dibujarTablero(g2d);
    }

    /**
     * Dibuja las 2 aspas horizontales (uncluyendo centro)
     */
    private void dibujarAspaHorizontal(Graphics2D g2d, int w, int h, int gap, int inicio, int casillas) {
        int y = (this.alto / 2) - gap - h;
        int x = inicio;
        int aux = x;
        int auy = y;

        //Creando esquinas
        g2d.fill(crearEsquina(x - gap - w, y, w * 2, h * 2, 90, 90));
        g2d.fill(crearEsquina(x - gap - w, y + gap, w * 2, h * 2, 180, 90));

        //Creando las casillas
        g2d.setColor(color);
        for (int i = 0; i < casillas; i++) {
            if (i == casillas / 2) {
                y += h + gap;
                x = inicio;
            }
            g2d.fillRect(x, y, w, h);
            x += w + gap;
        }

        //Creando esquinas
        g2d.fill(crearEsquina(x - w, y - h - gap, w * 2, h * 2, 0, 90));
        g2d.fill(crearEsquina(x - w, y - h, w * 2, h * 2, 270, 90));

        //Creando los triangulo izquierdos
        g2d.setColor(Color.MAGENTA);
        g2d.fillPolygon(crearTriangulo(aux + w + (gap / 2) * 2, auy + h, w / 2, h, -1));
        g2d.fillPolygon(crearTriangulo(aux + w + (gap / 2) * 2, auy + h + gap, w / 2, h, 1));

        //Creando los triangulo derechos
        g2d.setColor(Color.MAGENTA);
        g2d.fillPolygon(crearTriangulo(x - w - gap - gap, y - gap, w / 2, h, -1));
        g2d.fillPolygon(crearTriangulo(x - w - gap - gap, y, w / 2, h, 1));

        g2d.setColor(color);
    }

    /**
     * Dibuja las 2 aspas verticales (uncluyendo centro)
     */
    private void dibujarAspaVertical(Graphics2D g2d, int w, int h, int gap, int inicio, int casillas) {
        int y = inicio;
        int x = this.ancho / 2 - w + gap;
        int aux = x;
        int auy = y;

        //Creando esquinas
        g2d.fill(crearEsquina(x, y - gap - h, w * 2, h * 2, 90, 90));
        g2d.fill(crearEsquina(x + gap, y - gap - h, w * 2, h * 2, 0, 90));

        //Creando las casillas
        g2d.setColor(color);
        for (int i = 0; i < casillas; i++) {
            if (i == casillas / 2) {
                x += w + gap;
                y = inicio;
            }
            g2d.fillRect(x, y, w, h);
            y += h + gap;
        }

        //Creando esquinas
        g2d.fill(crearEsquina(x - gap - w, y - h, w * 2, h * 2, 180, 90));
        g2d.fill(crearEsquina(x - w, y - h, w * 2, h * 2, 270, 90));

        //Creando los triangulos de arriba
        g2d.setColor(Color.MAGENTA);
        g2d.fillPolygon(crearTriangulo(aux + w, auy + h, w, h / 2, -2));
        g2d.fillPolygon(crearTriangulo(aux + w + gap, auy + h, w, h / 2, 2));

        //Creando triangulos de abajo
        g2d.setColor(Color.MAGENTA);
        g2d.fillPolygon(crearTriangulo(x - gap, y - h - gap, w, h / 2, -2));
        g2d.fillPolygon(crearTriangulo(x, y - h - gap, w, h / 2, 2));

        g2d.setColor(color);

    }

    /**
     * Crea un triangulo para ingresar en los rectangulos
     *
     * @param x punto x de inicio
     * @param y punto y de inicio
     * @param w ancho del triangulo
     * @param h alto del triangulo
     * @param dir dirección; 1 arriba, -1 abajo, 2 izquierda, -2 derecha
     */
    private Polygon crearTriangulo(int xi, int yi, int ancho, int alto, int dir) {
        int[] xPuntos = {}, yPuntos = {};

        switch (dir) {
            case 1:
                xPuntos = new int[]{xi, xi + ancho, xi - ancho};
                yPuntos = new int[]{yi, yi + alto, yi + alto};
                break;
            case -1:
                xPuntos = new int[]{xi, xi + ancho, xi - ancho};
                yPuntos = new int[]{yi, yi - alto, yi - alto};
                break;
            case 2:
                xPuntos = new int[]{xi, xi + ancho, xi + ancho};
                yPuntos = new int[]{yi, yi - alto, yi + alto};
                break;
            case -2:
                xPuntos = new int[]{xi, xi - ancho, xi - ancho};
                yPuntos = new int[]{yi, yi - alto, yi + alto};
                break;
        }

        return new Polygon(xPuntos, yPuntos, 3);

    }

    /**
     * Crea un circulo desde el angulo indicado hasta la suma del segundo angulo
     */
    private Arc2D crearEsquina(int xi, int yi, int ancho, int alto, int angInicio, int angFin) {
        return new Arc2D.Double(xi, yi, ancho, alto, angInicio, angFin, Arc2D.PIE);
    }

    public void setFichas(ArrayList<Ficha> fichas) {
        this.fichas = fichas;
    }
    
    private void generarPosiciones() {
        //Coordenadas para la posición cero
        int inicioy = alto / 2 - gap - h;
        int iniciox = ancho / 2 - gap - (w / 2);
        int aux = iniciox + (w + gap) * 2, auy = inicioy;

        int c = casillasAspa * 2 + 2;
        int c2 = casillasAspa + 2;
        
        //Para la posición cero es la casilla del centro superior izquierdo
        //Se generan de izquierda a derecha, primero las verticales
        for (int i = 0; i < casillasAspa * 2 + 4; i++) {
            if (i >= ((casillasAspa * 2 + 4) / 2)) {
                //ahora toma las posiciones de c
                posiciones[c] = new Casilla(new Point(iniciox, inicioy));
                posiciones[c].setCasillaJugador(-1);
                c++;
            } else {
                //toma las posiciones de i
                posiciones[i] = new Casilla(new Point(iniciox, inicioy));
                posiciones[i].setCasillaJugador(-1);
            }

            //Cuando restar coordenadas en y 
            if (i < casillasAspa / 2 || (i >= ((casillasAspa * 2 + 3) - (casillasAspa / 2)) && i <= casillasAspa * 2 + 3)) {
                inicioy = inicioy - h - gap;
            }

            //Cuando sumar coordenadas en y
            if ((i >= (casillasAspa / 2 + 1) && i <= (casillasAspa * 2 + 4) / 2 - 1) || (i >= (casillasAspa * 2 + 4) / 2 && i < (casillasAspa * 2 + 4) / 2 + casillasAspa / 2)) {
                inicioy = inicioy + h + gap;
            }

            //Cuando sumar coordenadas en x
            if (i == casillasAspa / 2) {
                iniciox = iniciox + w + gap;
            }

            //Cuando restar coordenadas en x
            if (i == (casillasAspa * 2 + 4) / 2 + casillasAspa / 2) {
                iniciox = iniciox - w - gap;
            }
        }

        c = casillasAspa * 3 + 4;

        //Generar posiciones horizontales
        for (int i = 0; i < casillasAspa * 2; i++) {
            //toma las posiciones de c
            if (i >= casillasAspa) {
                posiciones[c] = new Casilla(new Point(aux, auy));
                posiciones[c].setCasillaJugador(-1);
                c++;
            } else {
                posiciones[c2] = new Casilla(new Point(aux, auy));
                posiciones[c2].setCasillaJugador(-1);
                c2++;
            }

            //Cuando sumar coordenadas en x
            if (i < casillasAspa / 2 - 1 || i >= casillasAspa + casillasAspa / 2) {
                aux = aux + w + gap;
            }

            //cuando restar coordenadas en x
            if (i > ((casillasAspa / 2) - 1) && i < (casillasAspa + (casillasAspa / 2)-1)) {
                aux = aux - w - gap;
            }

            //cuando sumar cooordenadas en y
            if (i == casillasAspa / 2 - 1) {
                auy = auy + h + gap;
            }

            //cuando restar coordenadas en y
            if (i == casillasAspa + (casillasAspa / 2) - 1) {
                auy = auy - h - gap;
            }

            if (i == casillasAspa-1) {
                aux = aux - (w + gap)*2;
            }

        }
        
        //determinando posiciones especiales
        
        //posiciones de las esquinas y posiciones con triangulo
        c = 1;
        for (int i = 0; i < 4; i++) {
            if(i>0){
                //Posiciones de esquinas
                posiciones[(casillasAspa*i+casillasAspa/2+c)].setEsquina(true);
                posiciones[(casillasAspa*i+casillasAspa/2+c+1)].setEsquina(true);
                //Posiciones de triangulo
                posiciones[(casillasAspa*i+casillasAspa/2+c-1)].setTriangulo(true);
                posiciones[(casillasAspa*i+casillasAspa/2+c-2)].setTriangulo(true);
                posiciones[(casillasAspa*i+casillasAspa/2+c+2)].setTriangulo(true);
                posiciones[(casillasAspa*i+casillasAspa/2+c+3)].setTriangulo(true);
                c++;
            }else{
                //Posiciones de esquinas
                posiciones[(casillasAspa/2*c)].setEsquina(true);
                posiciones[(casillasAspa/2*c+1)].setEsquina(true);
                //Posiciones de triangulo
                posiciones[(casillasAspa/2*c-1)].setTriangulo(true);
                posiciones[(casillasAspa/2*c-2)].setTriangulo(true);
                posiciones[(casillasAspa/2*c+2)].setTriangulo(true);
                posiciones[(casillasAspa/2*c+3)].setTriangulo(true);
            
            }
        }
        
        c = 1;
        //determinar centros y casillas de inicio para cada jugador
        for (int i = 0; i < 4; i++) {
            if(i>0){
                posiciones[casillasAspa*i+c].setMedio(true);
                posiciones[casillasAspa*i+c+1].setInicio(true);
                posiciones[casillasAspa*i+c+1].setCasillaJugador(i);
                c++;
            }else{
                posiciones[i].setMedio(true);
                posiciones[i+1].setInicio(true);
                posiciones[i+1].setCasillaJugador(i);
            }
        }
        
    }
    
}
