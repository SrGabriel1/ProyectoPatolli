/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.pruebaservidor;

import java.util.List;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author USER
 */
public class PruebaServidor extends Observable implements Runnable{
    
    private ArrayList<DatosUsuario> clientes = new ArrayList<>();
    private int puerto = 4444;
    ServerSocket servidor = null;
    Socket sc = null;
    DataInputStream in;

    @Override
    public void run() {
        agregarUsuariosAPartida();
        partidaEnJuego();
        
    }
    
    public void agregarUsuariosAPartida(){
        try{
            servidor =new ServerSocket(puerto);
            System.out.println("Servidor iniciado");
            
            while(true){
                sc=servidor.accept();
                System.out.println("Validando usuario");
                in=new DataInputStream(sc.getInputStream());
                String mensaje=in.readUTF();
                if(validarUsuario(mensaje)){
                    clientes.add(new DatosUsuario(sc, mensaje));
                    this.setChanged();
                    this.notifyObservers(mensaje);
                    this.clearChanged();
                }else{
                    sc.close();
                }
            }
        }catch(Exception e){
            
        }
    }
    
    public boolean validarUsuario(String nombre){
        for(DatosUsuario cliente:clientes){
            if(cliente.getNombre().endsWith(nombre)){
                return true;
            }
        }
        return false;
    }
    
    public void partidaEnJuego(){
        
    }
}
