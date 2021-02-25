/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.VistaSimulador;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

/**
 *
 * @author Giselle Ferreira
 */
public class Almacen {
    
//    Variables estáticas de los parámetros iniciales del simulador que se toman del archivo txt
    public static int duracionDia;
    public static int diasDespacho; 
    public static int capacidadBotones; 
    public static int capacidadPantallas; 
    public static int capacidadJoysticks;
    public static int capacidadTarjetasSD; 
    public static int productoresInicialesBotones;
    public static int productoresInicialesPantallas; 
    public static int productoresInicialesJoysticks;
    public static int productoresInicialesTarjetasSD; 
    public static int productoresMaximosBotones;
    public static int productoresMaximosPantallas; 
    public static int productoresMaximosJoysticks;
    public static int productoresMaximosTarjetasSD; 
    public static int ensambladoresIniciales;
    public static int ensambladoresMaximos;
    
//    Variables de la vista
    public static int numProductoresBotones;
    public static int numProductoresPantallas;
    public static int numProductoresJoysticks;
    public static int numProductoresTarjetasSD;
    public static volatile int cantidadBotones;
    public static volatile int cantidadPantallasNormales;
    public static volatile int cantidadPantallasTactiles;
    public static volatile int cantidadJoysticks;
    public static volatile int cantidadTarjetasSD;
    public static int numEnsambladores;
    public static int cantidadConsolas;
    public static int diasRestantesDespacho;
    public static String estadoJefe;
    public static String estadoGerente;
    
//    Semáforos de exclusión mutua
    public Semaphore semExclusionBotones;
    public Semaphore semExclusionPantallas;
    public Semaphore semExclusionJoysticks;
    public Semaphore semExclusionTarjetasSD;
    
//    Semáforos producción
    public Semaphore semProduccionBotones;
    public Semaphore semProduccionPantallas;
    public Semaphore semProduccionJoysticks;
    public Semaphore semProduccionTarjetasSD;
    
//    Semáforos de ensamblador
    public Semaphore semEnsamblajeBotones;
    public Semaphore semEnsamblajePantallas;
    public Semaphore semEnsamblajeJoysticks;
    public Semaphore semEnsamblajeTarjetasSD;
    
//    Semáforos lector / escritor
    public Semaphore semJefe;
    public Semaphore semGerente;
    
//    Array con para los productores
    public Productor[] productoresBotones;
    public Productor[] productoresPantallas;
    public Productor[] productoresJoysticks;
    public Productor[] productoresTarjertasSD;
    
//    Array para los ensambladores
    public Ensamblador[] ensambladores;
    
    
//    Constructor del almacen
    public Almacen() {
        
//        Si el archivo se lee correctamente entonces se inicia el almacen
        if(Archivo.leerArchivo()){
            
            VistaSimulador vista = new VistaSimulador();
            
//            Inicializar contadores de producción
            this.cantidadBotones = 0;
            this.cantidadPantallasNormales = 0;
            this.cantidadPantallasTactiles = 0;
            this.cantidadJoysticks = 0;
            this.cantidadTarjetasSD = 0;
            this.cantidadConsolas = 0;
            
//            Inicializar cantidad de productores
            this.numProductoresBotones = this.productoresInicialesBotones;
            this.numProductoresJoysticks = this.productoresInicialesJoysticks;
            this.numProductoresPantallas = this.productoresInicialesPantallas;
            this.numProductoresTarjetasSD = this.productoresInicialesTarjetasSD;
            this.numEnsambladores = this.ensambladoresIniciales;
            
//            Inicializar semáforos de exclusión mutua
            this.semExclusionBotones = new Semaphore(1);
            this.semExclusionJoysticks = new Semaphore(1);
            this.semExclusionPantallas = new Semaphore(1);
            this.semExclusionTarjetasSD = new Semaphore(1);
            
//            Inicializar semáforos de lector / escritor
            this.semJefe = new Semaphore(1);
            this.semGerente = new Semaphore(1);
            
//            Inicializar semáforos de produccion con capacidad máxima
            this.semProduccionBotones = new Semaphore(capacidadBotones);
            this.semProduccionPantallas = new Semaphore(capacidadPantallas);
            this.semProduccionJoysticks = new Semaphore(capacidadJoysticks);
            this.semProduccionTarjetasSD = new Semaphore(capacidadTarjetasSD);
            
//            Inicializar semáforos de ensamblaje en 0 porque la capacidad de consumo inicial es 0
            this.semEnsamblajeBotones = new Semaphore(0);
            this.semEnsamblajePantallas = new Semaphore(0);
            this.semEnsamblajeJoysticks = new Semaphore(0);
            this.semEnsamblajeTarjetasSD = new Semaphore(0);
            
//            Inicializar los arrays de los productores
            this.productoresBotones = new Productor[productoresMaximosBotones];
            this.productoresPantallas = new Productor[productoresMaximosPantallas];
            this.productoresJoysticks = new Productor[productoresMaximosJoysticks];
            this.productoresTarjertasSD = new Productor[productoresMaximosTarjetasSD];
            
//            Inicializar los arrays de ensambladores
            this.ensambladores = new Ensamblador[ensambladoresMaximos];
            
//            ger = new Gerente(semJG, semGE);
//            jef = new Jefe(semJG);
            iniciarProduccion();
//            vista.actualizar();
            
//        Si el archivo no se lee correctamente se cierra el programa
        }else{
            System.exit(0);
        }
        
    }
    
//    Método para iniciar la producción de la fábrica
    public void iniciarProduccion(){
        
        for (int i = 0; i < productoresInicialesBotones; i++) {
            productoresBotones[i] = new Productor(1, 1, true, semExclusionBotones, semProduccionBotones, semEnsamblajeBotones);
            productoresBotones[i].start();
        }
            
        for (int i = 0; i < productoresInicialesPantallas; i++) {
            productoresPantallas[i] = new Productor(2, 1, true, semExclusionPantallas, semProduccionPantallas, semEnsamblajePantallas);
            productoresPantallas[i].start();
        }
            
        for (int i = 0; i < productoresInicialesJoysticks; i++) {
            productoresJoysticks[i] = new Productor(3, 2, true, semExclusionJoysticks, semProduccionJoysticks, semEnsamblajeJoysticks);
            productoresJoysticks[i].start();
        }
            
        for (int i = 0; i < productoresInicialesTarjetasSD; i++) {
            productoresTarjertasSD[i] = new Productor(4, 3, true, semExclusionTarjetasSD, semProduccionTarjetasSD, semEnsamblajeTarjetasSD);
            productoresTarjertasSD[i].start();
        }
        
        for (int i = 0; i < ensambladoresIniciales; i++) {
            ensambladores[i] = new Ensamblador(semExclusionBotones, semExclusionPantallas, semExclusionJoysticks, semExclusionTarjetasSD, semProduccionBotones, semProduccionPantallas, semProduccionJoysticks, semProduccionTarjetasSD, semEnsamblajeBotones, semEnsamblajePantallas, semEnsamblajeJoysticks, semEnsamblajeTarjetasSD, semGerente);
            ensambladores[i].start();
        }
        
//        ger.start();
//        jef.start();
    }
    
    public void contratar(int tipo){
        switch(tipo){
            case 1:
                for(int i = 0; i < productoresBotones.length; i++){
                    if(productoresBotones[i] == null){
                        productoresBotones[i] = new Productor(1, 1, true, semExclusionBotones, semProduccionBotones, semEnsamblajeBotones);
                        productoresBotones[i].start();
                        numProductoresBotones++;
                        break; 
                    }
                    if(!productoresBotones[i].isIsContratado()){
                        productoresBotones[i].setIsContratado(true);
                        productoresBotones[i].start();
                        numProductoresBotones++;
                        break;
                    }
                }
                break;
                
            case 2:
                for(int i = 0; i < productoresPantallas.length; i++){
                    if(productoresPantallas[i] == null){
                        productoresPantallas[i] = new Productor(2, 1, true, semExclusionPantallas, semProduccionPantallas, semEnsamblajePantallas);
                        productoresPantallas[i].start();
                        numProductoresPantallas++;
                        break; 
                    }
                    if(!productoresPantallas[i].isIsContratado()){
                        productoresPantallas[i].setIsContratado(true);
                        productoresPantallas[i].start();
                        numProductoresPantallas++;
                        break;
                    }
                }
                break;
                
            case 3:
                for(int i = 0; i < productoresJoysticks.length; i++){
                    if(productoresJoysticks[i] == null){
                        productoresJoysticks[i] = new Productor(3, 2, true, semExclusionJoysticks, semProduccionJoysticks, semEnsamblajeJoysticks);
                        productoresJoysticks[i].start();
                        numProductoresJoysticks++;
                        break; 
                    }
                    if(!productoresJoysticks[i].isIsContratado()){
                        productoresJoysticks[i].setIsContratado(true);
                        productoresJoysticks[i].start();
                        numProductoresJoysticks++;
                        break;
                    }
                }
                break;
                
            case 4:
                for(int i = 0; i < productoresTarjertasSD.length; i++){
                    if(productoresTarjertasSD[i] == null){
                        productoresTarjertasSD[i] = new Productor(4, 3, true, semExclusionTarjetasSD, semProduccionTarjetasSD, semEnsamblajeTarjetasSD);
                        productoresTarjertasSD[i].start();
                        numProductoresTarjetasSD++;
                        break; 
                    }
                    if(!productoresTarjertasSD[i].isIsContratado()){
                        productoresTarjertasSD[i].setIsContratado(true);
                        productoresTarjertasSD[i].start();
                        numProductoresTarjetasSD++;
                        break;
                    }
                }
                break;
                
            case 5:
                for(int i = 0; i < ensambladores.length; i++){
                    if(ensambladores[i] == null){
                        ensambladores[i] = new Ensamblador(semExclusionBotones, semExclusionPantallas, semExclusionJoysticks, semExclusionTarjetasSD, semProduccionBotones, semProduccionPantallas, semProduccionJoysticks, semProduccionTarjetasSD, semEnsamblajeBotones, semEnsamblajePantallas, semEnsamblajeJoysticks, semEnsamblajeTarjetasSD, semGerente);
                        ensambladores[i].start();
                        numEnsambladores++;
                        break; 
                    }
                    if(!ensambladores[i].isIsContratado()){
                        ensambladores[i].setIsContratado(true);
                        ensambladores[i].start();
                        numEnsambladores++;
                        break;
                    }
                }
                break;
                
            default:
                JOptionPane.showMessageDialog(null, "Error, al contratar.", "ERROR DE CONTRATACIÓN", JOptionPane.ERROR_MESSAGE);

        }
    }
                         
    public void despedir(int tipo){
        switch(tipo){
            case 1:
                for(int i = productoresBotones.length; i >= 0; i++){
                    if(productoresBotones[i] != null){
                        productoresBotones[i].setIsContratado(false);
                        numProductoresBotones--;
                        break;
                    }
                }
                break;
                
            case 2:
                for(int i = productoresPantallas.length; i >= 0; i++){
                    if(productoresPantallas[i] != null){
                        productoresPantallas[i].setIsContratado(false);
                        numProductoresPantallas--;
                        break;
                    }
                }
                break;
                
            case 3:
                for(int i = productoresJoysticks.length; i >= 0; i++){
                    if(productoresJoysticks[i] != null){
                        productoresJoysticks[i].setIsContratado(false);
                        numProductoresJoysticks--;
                        break;
                    }
                }
                break;
                
            case 4:
                for(int i = productoresTarjertasSD.length; i >= 0; i++){
                    if(productoresTarjertasSD[i] != null){
                        productoresTarjertasSD[i].setIsContratado(false);
                        numProductoresTarjetasSD--;
                        break;
                    }
                }
                break;
                
            case 5:
                for(int i = ensambladores.length; i >= 0; i++){
                    if(ensambladores[i] != null){
                        ensambladores[i].setIsContratado(false);
                        numEnsambladores--;
                        break;
                    }
                }
                break;
                
            default:
                JOptionPane.showMessageDialog(null, "Error, al despedir.", "ERROR DE DESPIDO", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
