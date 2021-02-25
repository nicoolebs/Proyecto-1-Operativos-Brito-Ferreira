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
public class Ensamblador extends Thread{
    
    private Semaphore semExclusionBotones, semExclusionPantallas, semExclusionJoysticks, semExclusionTarjetasSD;
    private Semaphore semProduccionBotones, semProduccionPantallas, semProduccionJoysticks, semProduccionTarjetasSD;
    private Semaphore semEnsamblajeBotones, semEnsamblajePantallas, semEnsamblajeJoysticks, semEnsamblajeTarjetasSD;
    private Semaphore semGerente;
    private boolean isContratado, isEnsamblado;

    public Ensamblador(Semaphore semExclusionBotones, Semaphore semExclusionPantallas, Semaphore semExclusionJoysticks, Semaphore semExclusionTarjetasSD, Semaphore semProduccionBotones, Semaphore semProduccionPantallas, Semaphore semProduccionJoysticks, Semaphore semProduccionTarjetasSD, Semaphore semEnsamblajeBotones, Semaphore semEnsamblajePantallas, Semaphore semEnsamblajeJoysticks, Semaphore semEnsamblajeTarjetasSD, Semaphore semGerente) {
        this.semExclusionBotones = semExclusionBotones;
        this.semExclusionPantallas = semExclusionPantallas;
        this.semExclusionJoysticks = semExclusionJoysticks;
        this.semExclusionTarjetasSD = semExclusionTarjetasSD;
        this.semProduccionBotones = semProduccionBotones;
        this.semProduccionPantallas = semProduccionPantallas;
        this.semProduccionJoysticks = semProduccionJoysticks;
        this.semProduccionTarjetasSD = semProduccionTarjetasSD;
        this.semEnsamblajeBotones = semEnsamblajeBotones;
        this.semEnsamblajePantallas = semEnsamblajePantallas;
        this.semEnsamblajeJoysticks = semEnsamblajeJoysticks;
        this.semEnsamblajeTarjetasSD = semEnsamblajeTarjetasSD;
        this.semGerente = semGerente;
        this.isContratado = true;
        this.isEnsamblado = false;
    }
    
    public void run(){
        while(this.isContratado){
            
            try {
                this.semEnsamblajeBotones.acquire(5);
                this.semEnsamblajePantallas.acquire(2);
                this.semEnsamblajeJoysticks.acquire(2);
                this.semEnsamblajeTarjetasSD.acquire();
                
                    this.semExclusionBotones.acquire();
                    this.semExclusionPantallas.acquire();
                    this.semExclusionJoysticks.acquire();
                    this.semExclusionTarjetasSD.acquire();
                    
                        if(this.isContratado){
                            
                            Almacen.cantidadBotones -= 5;
                            Almacen.cantidadPantallasNormales--;
                            Almacen.cantidadPantallasTactiles--;
                            Almacen.cantidadJoysticks -= 2;
                            Almacen.cantidadTarjetasSD--;
                            
                        }
                    
                    this.semExclusionBotones.release();
                    this.semExclusionPantallas.release();
                    this.semExclusionJoysticks.release();
                    this.semExclusionTarjetasSD.release();
                
                this.semProduccionBotones.release(5);
                this.semProduccionPantallas.release(2);
                this.semProduccionJoysticks.release(2);
                this.semProduccionTarjetasSD.release();
                
                Thread.sleep(Almacen.duracionDia);
                this.semGerente.acquire();
                    
                    Almacen.cantidadConsolas++;
                
                this.semGerente.release();
                
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error, los ensambladores estan fallando.", "ERROR DE ENSAMBLAJE", JOptionPane.ERROR_MESSAGE);
            }
            
            
        }
    }

    public Semaphore getSemExclusionBotones() {
        return semExclusionBotones;
    }

    public void setSemExclusionBotones(Semaphore semExclusionBotones) {
        this.semExclusionBotones = semExclusionBotones;
    }

    public Semaphore getSemExclusionPantallas() {
        return semExclusionPantallas;
    }

    public void setSemExclusionPantallas(Semaphore semExclusionPantallas) {
        this.semExclusionPantallas = semExclusionPantallas;
    }

    public Semaphore getSemExclusionJoysticks() {
        return semExclusionJoysticks;
    }

    public void setSemExclusionJoysticks(Semaphore semExclusionJoysticks) {
        this.semExclusionJoysticks = semExclusionJoysticks;
    }

    public Semaphore getSemExclusionTarjetasSD() {
        return semExclusionTarjetasSD;
    }

    public void setSemExclusionTarjetasSD(Semaphore semExclusionTarjetasSD) {
        this.semExclusionTarjetasSD = semExclusionTarjetasSD;
    }

    public Semaphore getSemProduccionBotones() {
        return semProduccionBotones;
    }

    public void setSemProduccionBotones(Semaphore semProduccionBotones) {
        this.semProduccionBotones = semProduccionBotones;
    }

    public Semaphore getSemProduccionPantallas() {
        return semProduccionPantallas;
    }

    public void setSemProduccionPantallas(Semaphore semProduccionPantallas) {
        this.semProduccionPantallas = semProduccionPantallas;
    }

    public Semaphore getSemProduccionJoysticks() {
        return semProduccionJoysticks;
    }

    public void setSemProduccionJoysticks(Semaphore semProduccionJoysticks) {
        this.semProduccionJoysticks = semProduccionJoysticks;
    }

    public Semaphore getSemProduccionTarjetasSD() {
        return semProduccionTarjetasSD;
    }

    public void setSemProduccionTarjetasSD(Semaphore semProduccionTarjetasSD) {
        this.semProduccionTarjetasSD = semProduccionTarjetasSD;
    }

    public Semaphore getSemEnsamblajeBotones() {
        return semEnsamblajeBotones;
    }

    public void setSemEnsamblajeBotones(Semaphore semEnsamblajeBotones) {
        this.semEnsamblajeBotones = semEnsamblajeBotones;
    }

    public Semaphore getSemEnsamblajePantallas() {
        return semEnsamblajePantallas;
    }

    public void setSemEnsamblajePantallas(Semaphore semEnsamblajePantallas) {
        this.semEnsamblajePantallas = semEnsamblajePantallas;
    }

    public Semaphore getSemEnsamblajeJoysticks() {
        return semEnsamblajeJoysticks;
    }

    public void setSemEnsamblajeJoysticks(Semaphore semEnsamblajeJoysticks) {
        this.semEnsamblajeJoysticks = semEnsamblajeJoysticks;
    }

    public Semaphore getSemEnsamblajeTarjetasSD() {
        return semEnsamblajeTarjetasSD;
    }

    public void setSemEnsamblajeTarjetasSD(Semaphore semEnsamblajeTarjetasSD) {
        this.semEnsamblajeTarjetasSD = semEnsamblajeTarjetasSD;
    }

    public boolean isIsContratado() {
        return isContratado;
    }

    public void setIsContratado(boolean isContratado) {
        this.isContratado = isContratado;
    }

    public boolean isIsEnsamblado() {
        return isEnsamblado;
    }

    public void setIsEnsamblado(boolean isEnsamblado) {
        this.isEnsamblado = isEnsamblado;
    }
    
    
}
