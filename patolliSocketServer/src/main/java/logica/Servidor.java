/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import conexion.HiloCliente;
import conexion.ManejadorClientes;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author USER
 */
public class Servidor implements Runnable{
    private int puerto = 4444;
    ServerSocket servidor = null;
    ManejadorClientes manejadorClientes;
    
    public Servidor() {
        try {
            this.servidor = new ServerSocket(puerto);
            this.manejadorClientes = new ManejadorClientes();
        } catch (IOException e) {
            System.err.println("Error al inicializar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Server iniciado!!");
            while (true) {
                Socket clientSocket = servidor.accept();
                System.out.println("Nuevo cliente conectado");
                HiloCliente hiloCliente = new HiloCliente(clientSocket,manejadorClientes);
                new Thread(hiloCliente).start();
                manejadorClientes.agregarCliente(hiloCliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
