/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Giselle Ferreira
 */
public class Jefe extends Thread{
    
    private Semaphore semJefe;

    public Jefe(Semaphore semJefe) {
        this.semJefe = semJefe;
    }
    
    public void run(){
        
        while(true){
            
            Almacen.estadoJefe = "Esperando al Gerente";
                
            try {
                
                this.semJefe.acquire();
                
                    if(Almacen.diasRestantesDespacho != 0){
                        
                        Almacen.estadoJefe = "Actualizando días restantes";
                        Thread.sleep(Almacen.duracionDia * ((6 * 100) / 24));
                        Almacen.diasRestantesDespacho--;
                        
                    }
                
                this.semJefe.release();
                
                Almacen.estadoJefe = "Durmiendo la siesta";
                Thread.sleep(Almacen.duracionDia * ((18 * 100) / 24));
                
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error, el jefe está fallando.", "ERROR DEL JEFE", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
    }
}
