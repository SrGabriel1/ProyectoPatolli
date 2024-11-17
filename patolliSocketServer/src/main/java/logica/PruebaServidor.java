/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package logica;

import modelo.ClienteConectado;
import conexion.Mensaje;
import java.util.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class PruebaServidor extends Observable implements Runnable {

    private ArrayList<ClienteConectado> clientes = new ArrayList<>();
    private int puerto = 4444;
    ServerSocket servidor = null;
    Socket sc = null;
    boolean listening = true;
    int capacidadMaxima=4;

    @Override
    public void run() {
        try {
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");
            agregarUsuariosAPartida();

        } catch (IOException ex) {
            Logger.getLogger(PruebaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void agregarUsuariosAPartida() {
        try {
            
            
            while (listening) {
                Socket clientSocket = servidor.accept();
                System.out.println("Nuevo cliente conectado");
                RegistradorUsuario registradorUsuario = new RegistradorUsuario(clientSocket);
                new Thread(registradorUsuario).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void usuariosEnLobby(){
        try{
            while(true){
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void partidaEnJuego() {

    }
    
    private void informarATodosLosUsuarios(String tipo,Object objeto,Socket excepcion) {
        try {
            for (int i = 0; i < clientes.size(); i++) {
                if(clientes.get(i).getSocket()!=excepcion){
                    ObjectOutputStream out = clientes.get(i).getOut();
                    out.writeObject(new Mensaje(tipo, objeto));
                    out.flush();
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void notificarALaInterfaz(Object o) {
        this.setChanged();
        this.notifyObservers(o);
        this.clearChanged();
    }


    
    private class ManejadorLobby implements Runnable{

        @Override
        public void run() {
        }
        
    }

    
}
