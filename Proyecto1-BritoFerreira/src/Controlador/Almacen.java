/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.VistaSimulador;
import java.util.concurrent.Semaphore;

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
    public int numProductoresBotones;
    public int numProductoresPantallas;
    public int numProductoresJoysticks;
    public int numProductoresTarjetasSD;
    public static volatile int cantidadBotones;
    public static volatile int cantidadPantallasNormales;
    public static volatile int cantidadPantallasTactiles;
    public static volatile int cantidadJoysticks;
    public static volatile int cantidadTarjetasSD;
    public int numEnsambladores;
    public int cantidadConsolas;
    public int diasRestantesDespacho;
    public String estadoJefe;
    public String estadoGerente;
    
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
            
            
            
//        Si el archivo no se lee correctamente se cierra el programa
        }else{
            System.exit(0);
        }
        
    }
    
    public void crearProductores(){
        
        
    }
                           
    
    
    
}
