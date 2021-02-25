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
    private int tiempoProduccion;
    private boolean isContratado;
    private Semaphore semExclusion, semProduccion, semEnsamblaje;

    public Productor(int tipoPieza, int tiempoProduccion, boolean isContratado, Semaphore semExclusion, Semaphore semProduccion, Semaphore semEnsamblaje) {
        this.tipoPieza = tipoPieza;
        this.tiempoProduccion = tiempoProduccion;
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
                    Thread.sleep(Almacen.duracionDia * this.tiempoProduccion);
                    this.semExclusion.acquire();

                        switch (this.tipoPieza){

        //                    Producción de botones
                            case 1:
                                Almacen.cantidadBotones += 2;
                                break;

        //                    Producción de pantallas normales
                            case 2:
                                Almacen.cantidadPantallasNormales++;
                                break;

        //                    Producción de joysticks    
                            case 3:
                                Almacen.cantidadJoysticks++;
                                break;

        //                    Producción de lectores de tarjetas SD
                            case 4:
                                Almacen.cantidadTarjetasSD++;
                                break;

                        }

                    this.semExclusion.release();
                    
//                    Producción de la pantalla táctil
                    if(this.tipoPieza == 2){
                        this.semProduccion.acquire();
                            Thread.sleep(Almacen.duracionDia * this.tiempoProduccion * 2);
                            this.semExclusion.acquire();

                                Almacen.cantidadPantallasTactiles++;

                            this.semExclusion.release();
                        this.semEnsamblaje.release();
                    }
                
                this.semEnsamblaje.release();
                
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error, los productores estan fallando.", "ERROR DE PRODUCCIÓN", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
    }

    public int getTipoPieza() {
        return tipoPieza;
    }

    public void setTipoPieza(int tipoPieza) {
        this.tipoPieza = tipoPieza;
    }

    public int getTiempoProduccion() {
        return tiempoProduccion;
    }

    public void setTiempoProduccion(int tiempoProduccion) {
        this.tiempoProduccion = tiempoProduccion;
    }

    public boolean isIsContratado() {
        return isContratado;
    }

    public void setIsContratado(boolean isContratado) {
        this.isContratado = isContratado;
    }

    public Semaphore getSemExclusion() {
        return semExclusion;
    }

    public void setSemExclusion(Semaphore semExclusion) {
        this.semExclusion = semExclusion;
    }

    public Semaphore getSemProduccion() {
        return semProduccion;
    }

    public void setSemProduccion(Semaphore semProduccion) {
        this.semProduccion = semProduccion;
    }

    public Semaphore getSemEnsamblaje() {
        return semEnsamblaje;
    }

    public void setSemEnsamblaje(Semaphore semEnsamblaje) {
        this.semEnsamblaje = semEnsamblaje;
    }
    
    
}
