/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import conexion.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import modelo.ClienteConectado;

/**
 *
 * @author USER
 */
    public class RegistradorUsuario implements Runnable {

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
            clientes.add(new ClienteConectado(clientSocket, mensaje,out));
            notificarALaInterfaz(mensaje);

        }
        public boolean validarUsuario(String nombre) {
            for (ClienteConectado cliente : clientes) {
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
