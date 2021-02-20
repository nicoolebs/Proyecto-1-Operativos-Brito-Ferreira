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
public class Productor extends Thread{
    
    private int tipoPieza;
    private boolean isContratado;
    private Semaphore semExclusion, semProduccion, semEnsamblaje;

    public Productor(int tipoPieza, boolean isContratado, Semaphore semExclusion, Semaphore semProduccion, Semaphore semEnsamblaje) {
        this.tipoPieza = tipoPieza;
        this.isContratado = isContratado;
        this.semExclusion = semExclusion;
        this.semProduccion = semProduccion;
        this.semEnsamblaje = semEnsamblaje;
    }
    
    public void run(){
        
//        El productor funciona mientras no sea despedido
        while(isContratado){
            
            try {
                this.semProduccion.acquire();
                this.semExclusion.acquire();
                
                    switch (this.tipoPieza){

    //                    Producción de botones
                        case 1:
                            Thread.sleep(Almacen.duracionDia * 1);
                            Almacen.cantidadBotones += 2;
                            break;

    //                    Producción de pantallas normales y táctiles
                        case 2:
                            Thread.sleep(Almacen.duracionDia * 1);
                            Almacen.cantidadPantallasNormales++;
                            
                            this.semProduccion.acquire();
                            
                                Thread.sleep(Almacen.duracionDia * 2);
                                Almacen.cantidadPantallasTactiles++;
                            
                            this.semEnsamblaje.release();
                            
                            break;

    //                    Producción de joysticks    
                        case 3:
                            Thread.sleep(Almacen.duracionDia * 2);
                            Almacen.cantidadJoysticks++;
                            break;

    //                    Producción de lectores de tarjetas SD
                        case 4:
                            Thread.sleep(Almacen.duracionDia * 3);
                            Almacen.cantidadTarjetasSD++;
                            break;

                    }
                
                this.semExclusion.release();
                this.semEnsamblaje.release();
                
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error, los productores estan fallando.", "ERROR DE PRODUCCIÓN", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
    }
    
    
}
