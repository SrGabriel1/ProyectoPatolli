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

    public Ficha() {
    }

    public Ficha(Integer Posicion, String color, Jugador propetario) {
        this.Posicion = Posicion;
        this.color = color;
        this.propetario = propetario;
    }

    public Ficha(String color) {
        this.color = color;
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
