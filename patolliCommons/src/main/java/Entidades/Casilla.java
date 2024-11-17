/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.List;

/**
 *
 * @author Gabriel
 */
public abstract class Casilla {

    private int numero;
    private List<Ficha> fichasEnCasilla;
    private String tipoCasilla;

    public void fichaCae() {
    }

    public abstract void aplicarEfecto(Ficha ficha);
}
