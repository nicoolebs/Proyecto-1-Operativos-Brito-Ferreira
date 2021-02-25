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
public class Gerente extends Thread{
    
    private Semaphore semGerente, semJefe;

    public Gerente(Semaphore semGerente, Semaphore semJefe) {
        this.semGerente = semGerente;
        this.semJefe = semJefe;
    }
    
    public void run(){
        while(true){
            
            Almacen.estadoGerente = "Esperando al Jefe";
            
            try {
                this.semJefe.acquire();
                
                    Almacen.estadoGerente = "Verificando días";
                    
                    if(Almacen.diasRestantesDespacho != 0){
                        
                        Almacen.estadoGerente = "Durmiendo la siesta";
                        this.semJefe.release();
                        Thread.sleep(Almacen.duracionDia * ((2*100)/24));
                        
                    }else{
                        
                        this.semGerente.acquire();
                        
                            Almacen.estadoGerente = "Desplegando New 15SD XL";
                            Almacen.diasRestantesDespacho = Almacen.diasDespacho;
                            Almacen.cantidadConsolas = 0;
                            
                        this.semGerente.release();
                        this.semJefe.release();
                        
                    }
                
                
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error, el gerente está fallando.", "ERROR DEL GERENTE", JOptionPane.ERROR_MESSAGE);
            }
            
            
            
        }
    }
    
}
