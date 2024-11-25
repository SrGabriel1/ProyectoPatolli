/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import mensajes.SolicitudALobby;
import java.io.Serializable;

/**
 *
 * @author USER
 */
public class CondicionesPartida implements Serializable{
    public SolicitudALobby admin;
    public int jugadores;
    public int semillas;
    public int apuestas;

    public CondicionesPartida() {
    }

    public CondicionesPartida(SolicitudALobby admin, int jugadores, int semillas, int apuestas) {
        this.admin = admin;
        this.jugadores = jugadores;
        this.semillas = semillas;
        this.apuestas = apuestas;
    }

    public SolicitudALobby getAdmin() {
        return admin;
    }

    public void setAdmin(SolicitudALobby admin) {
        this.admin = admin;
    }

    

    public int getJugadores() {
        return jugadores;
    }

    public void setJugadores(int jugadores) {
        this.jugadores = jugadores;
    }

    public int getSemillas() {
        return semillas;
    }

    public void setSemillas(int semillas) {
        this.semillas = semillas;
    }

    public int getApuestas() {
        return apuestas;
    }

    public void setApuestas(int apuestas) {
        this.apuestas = apuestas;
    }
    
    
}
