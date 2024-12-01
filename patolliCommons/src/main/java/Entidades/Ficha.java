/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Gabriel
 */
public class Ficha {

    private Integer Posicion;
    private String color;
    private Jugador propetario;
    private boolean esGanadora; // Indica si la ficha ha llegado a la casilla final

    public Ficha() {
    }

    public Ficha(Integer Posicion, String color) {
        this.Posicion = Posicion;
        this.color = color;
    }

    public Ficha(Integer Posicion, String color, Jugador propetario) {
        this.Posicion = Posicion;
        this.color = color;
        this.propetario = propetario;
    }

    public Ficha(String color) {
        this.color = color;
    }

    public boolean EsGanadora() {
        return esGanadora;
    }

    public void setEsGanadora(boolean esGanadora) {
        this.esGanadora = esGanadora;
    }

    public Integer getPosicion() {
        return Posicion;
    }

    public void setPosicion(Integer Posicion) {
        this.Posicion = Posicion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Jugador getPropetario() {
        return propetario;
    }

    public void setPropetario(Jugador propetario) {
        this.propetario = propetario;
    }

}
