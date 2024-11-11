/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pruebaservidor;

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

    private ArrayList<DatosUsuario> clientes = new ArrayList<>();
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
            usuariosEnLobby();
            partidaEnJuego();

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

    private class RegistradorUsuario implements Runnable {

        private Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public RegistradorUsuario(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                while (true) {
                    Mensaje mensajeRecibido = (Mensaje) in.readObject();
                    if (mensajeRecibido.getTipo().equals("String")) {
                        String mensaje = (String) mensajeRecibido.getContenido();
                        if(clientes.size()==capacidadMaxima){
                            mandarLobyLleno(out);
                        }else if (validarUsuario(mensaje)) {
                            agregarClienteALobby(mensaje,out);
                            mandarUsuarioValido(out);
                            mandarNombresDeUsuario(out);
                            informarATodosLosUsuarios("Usuario agregado", mensaje,clientSocket);
                            if(clientes.size()==capacidadMaxima){
                                listening=false;
                            }
                        } else {
                            mandarUsuarioRepetido(out);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public void mandarLobyLleno(ObjectOutputStream out) throws IOException{
            out.writeObject(new Mensaje("Error", "Lobby lleno"));
            out.flush();
        }
        
        public void mandarUsuarioValido(ObjectOutputStream out) throws IOException {
            out.writeObject(new Mensaje("Validado", "Nombre de usuario valido"));
            out.flush();
        }
        
        public void mandarUsuarioRepetido(ObjectOutputStream out) throws IOException {
            out.writeObject(new Mensaje("Error", "Otro usuario ya cuenta con ese nombre"));
            out.flush();
        }
        public void mandarNombresDeUsuario(ObjectOutputStream out) throws IOException {
            out.writeObject(new Mensaje("Usuarios", nombresDeUsuarios()));
            out.flush();
        }
        
        public void agregarClienteALobby(String mensaje,ObjectOutputStream out){
            clientes.add(new DatosUsuario(clientSocket, mensaje,out));
            notificarALaInterfaz(mensaje);

        }
        public boolean validarUsuario(String nombre) {
            for (DatosUsuario cliente : clientes) {
                if (cliente.getNombre().equalsIgnoreCase(nombre)) {
                    return false;
                }
            }
            return true;
        }
        
        public String nombresDeUsuarios() {
            StringBuilder resultado = new StringBuilder();
            for (int i=0;i<clientes.size();i++) {
                System.out.println(clientes.get(i).getSocket());
                resultado.append(clientes.get(i).getNombre()).append(" ");
            }
            if (resultado.length() > 0) {
                resultado.setLength(resultado.length() - 1);
            }

            return resultado.toString();
        }
    }
    
    private class ManejadorLobby implements Runnable{

        @Override
        public void run() {
        }
        
    }

    
}
