package Controlador;

import Vista.VistaNintendo;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

//CLASE DEL ALMACEN DE NINTENDO
public class Almacen {
    
//    VARIABLES
    
//    Datos leídos del archivo TXT
    public static int duracionDia;
    public static int diasDespliegue; 
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
    
//    Datos que se muestran en la interfaz gráfica
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
    public static volatile int cantidadConsolas;
    public static volatile int cantidadConsolasUltimoDespliegue;
    public static volatile int diasRestantesDespliegue;
    public static volatile String estadoJefe;
    public static volatile String estadoGerente;
    
//    Semáforos de exclusión mutua para modificar las piezas producidas o consumidas de lis almacenes
    public static Semaphore semExclusionBotones;
    public static Semaphore semExclusionPantallas;
    public static Semaphore semExclusionJoysticks;
    public static Semaphore semExclusionTarjetasSD;
    
//    Semáforos de los productores que manejan los límites de producción por cada almacen
    public static Semaphore semProduccionBotones;
    public static Semaphore semProduccionPantallas;
    public static Semaphore semProduccionJoysticks;
    public static Semaphore semProduccionTarjetasSD;
    
//    Semáforos de los ensambladores que manejan las capacidades de consumo de cada almacen
    public static Semaphore semEnsamblajeBotones;
    public static Semaphore semEnsamblajePantallas;
    public static Semaphore semEnsamblajeJoysticks;
    public static Semaphore semEnsamblajeTarjetasSD;
    
//    Semáforos del Jefe que modifica el paso de los días y del gerente que realiza el despliegue de las consolas
    public static Semaphore semJefe;
    public static Semaphore semGerente;
    
//    Array para ubicar a los productores de cada tipo al momento de contratar o despedir
    public static Productor[] productoresBotones;
    public static Productor[] productoresPantallas;
    public static Productor[] productoresJoysticks;
    public static Productor[] productoresTarjertasSD;
    
//    Array para ubicar los ensambladores al momento de contratar o despedir
    public static Ensamblador[] ensambladores;
    
//    Hilos del Jefe y el Gerente
    public static Gerente gerente;
    public static Jefe jefe;
    
//    CONSTRUCTOR DEL ALMACEN
    public Almacen() {
        
//        Si el archivo se lee correctamente entonces se inicia la simulación del almacen
        if(Archivo.leerArchivo()){
            
//            Inicializar cantidad de productores con los valores iniciales del archivo txt
            this.numProductoresBotones = this.productoresInicialesBotones;
            this.numProductoresJoysticks = this.productoresInicialesJoysticks;
            this.numProductoresPantallas = this.productoresInicialesPantallas;
            this.numProductoresTarjetasSD = this.productoresInicialesTarjetasSD;
            this.numEnsambladores = this.ensambladoresIniciales;
            
//            Inicializar contadores de la producción del almacen
            this.cantidadBotones = 0;
            this.cantidadPantallasNormales = 0;
            this.cantidadPantallasTactiles = 0;
            this.cantidadJoysticks = 0;
            this.cantidadTarjetasSD = 0;
            this.cantidadConsolas = 0;
            this.cantidadConsolasUltimoDespliegue = 0;
            
//            Inicializar el contador de los días para el despliegue
            this.diasRestantesDespliegue = this.diasDespliegue;
            
//            Inicializar los estados del Jefe y del Gerente
            this.estadoGerente = "Recién contratado";
            this.estadoJefe = "Recién contratado";
            
//            Inicializar semáforos de exclusión mutua de los almacenes
            this.semExclusionBotones = new Semaphore(1);
            this.semExclusionJoysticks = new Semaphore(1);
            this.semExclusionPantallas = new Semaphore(1);
            this.semExclusionTarjetasSD = new Semaphore(1);
            
//            Inicializar semáforos de producción con la capacidad máxima de cada almacen, porque estos semáforos son los almacenes de cada pieza
            this.semProduccionBotones = new Semaphore(capacidadBotones);
            this.semProduccionPantallas = new Semaphore(capacidadPantallas);
            this.semProduccionJoysticks = new Semaphore(capacidadJoysticks);
            this.semProduccionTarjetasSD = new Semaphore(capacidadTarjetasSD);
            
//            Inicializar semáforos de ensamblaje en 0, porque la capacidad de consumo inicial es 0
            this.semEnsamblajeBotones = new Semaphore(0);
            this.semEnsamblajePantallas = new Semaphore(0);
            this.semEnsamblajeJoysticks = new Semaphore(0);
            this.semEnsamblajeTarjetasSD = new Semaphore(0);
            
//            Inicializar semáforos del Jefe y del Gerente
            this.semJefe = new Semaphore(1);
            this.semGerente = new Semaphore(1);
            
//            Inicializar los arrays de los productores con el tamaño especificado en el archivo txt
            this.productoresBotones = new Productor[productoresMaximosBotones];
            this.productoresPantallas = new Productor[productoresMaximosPantallas];
            this.productoresJoysticks = new Productor[productoresMaximosJoysticks];
            this.productoresTarjertasSD = new Productor[productoresMaximosTarjetasSD];
            
//            Inicializar los arrays de ensambladores con el tamaño especificado en el archivo txt
            this.ensambladores = new Ensamblador[ensambladoresMaximos];
            
//            Inicializar la interfaz gráfica
            VistaNintendo vista = new VistaNintendo();
            
//            Arrancar la producción de la simulación del almacen
            iniciarProduccion();
            
//            Poner la interfaz gráfica a actualizarse constantemente
            vista.actualizar();
            
        }else{
//        Si el archivo no se lee correctamente, se cierra el programa
            System.exit(0);
        }
        
    }
    
//    MÉTODO PARA ARRANCAR LA PRODUCCIÓN INICIAL DE LA SIMULACIÓN
    public void iniciarProduccion(){
        
//        Recorrer el array de los hilos de los productores de botones iniciales, inicializarlos y arrancar la ejecución
        for (int i = 0; i < productoresInicialesBotones; i++) {
            productoresBotones[i] = new Productor(1, 1, 2, semExclusionBotones, semProduccionBotones, semEnsamblajeBotones);
            productoresBotones[i].start();
        }
            
//        Recorrer el array de los hilos de los productores de pantallas iniciales, inicializarlos y arrancar la ejecución
        for (int i = 0; i < productoresInicialesPantallas; i++) {
            productoresPantallas[i] = new Productor(2, 1, 2, semExclusionPantallas, semProduccionPantallas, semEnsamblajePantallas);
            productoresPantallas[i].start();
        }
            
//        Recorrer el array de los hilos de los productores de joysticks iniciales, inicializarlos y arrancar la ejecución
        for (int i = 0; i < productoresInicialesJoysticks; i++) {
            productoresJoysticks[i] = new Productor(3, 2, 1, semExclusionJoysticks, semProduccionJoysticks, semEnsamblajeJoysticks);
            productoresJoysticks[i].start();
        }
            
//        Recorrer el array de los hilos de los productores de lectores de tarjetas SD iniciales, inicializarlos y arrancar la ejecución
        for (int i = 0; i < productoresInicialesTarjetasSD; i++) {
            productoresTarjertasSD[i] = new Productor(4, 3, 1, semExclusionTarjetasSD, semProduccionTarjetasSD, semEnsamblajeTarjetasSD);
            productoresTarjertasSD[i].start();
        }
        
//        Recorrer el array de los hilos de los ensambladores iniciales, inicializarlos y arrancar la ejecución
        for (int i = 0; i < ensambladoresIniciales; i++) {
            ensambladores[i] = new Ensamblador(semExclusionBotones, semExclusionPantallas, semExclusionJoysticks, semExclusionTarjetasSD, semProduccionBotones, semProduccionPantallas, semProduccionJoysticks, semProduccionTarjetasSD, semEnsamblajeBotones, semEnsamblajePantallas, semEnsamblajeJoysticks, semEnsamblajeTarjetasSD, semGerente);
            ensambladores[i].start();
        }
        
//            Inicializar los hilos del Jefe y del Gerente
        this.gerente = new Gerente(semGerente, semJefe);
        this.jefe = new Jefe(semJefe);
        
//        Arrancar los hilos del Jefe y del Gerente
        gerente.start();
        jefe.start();
    }
    
//    MÉTODO PARA CONTRATAR NUEVOS PRODUCTORES O ENSAMBLADORES
    public static void contratar(int tipo){
        
//        Condicional para contratar el tipo de productor o ensamblador correspondiente
        switch(tipo){
            
            case 1:
                
//            Contratar productor de botones
//            Recorrer el array de los productores de botones
//            Crear un nuevo productor en la última posición donde no hay un productor o hay uno que fue despedido
//            Inicializar un nuevo productor, arrancar el hilo y aumentar el contador de productores de botones
                
                for(int i = 0; i < productoresBotones.length; i++){
                    if(productoresBotones[i] == null || !productoresBotones[i].isIsContratado()){
                        productoresBotones[i] = new Productor(1, 1, 2, semExclusionBotones, semProduccionBotones, semEnsamblajeBotones);
                        productoresBotones[i].start();
                        numProductoresBotones++;
                        break; 
                    }
                }
                break;
                
            case 2:
                
//            Contratar productor de pantallas
//            Recorrer el array de los productores de pantallas
//            Crear un nuevo productor en la última posición donde no hay un productor o hay uno que fue despedido
//            Inicializar un nuevo productor, arrancar el hilo y aumentar el contador de productores de pantallas
                
                for(int i = 0; i < productoresPantallas.length; i++){
                    if(productoresPantallas[i] == null || !productoresPantallas[i].isIsContratado()){
                        productoresPantallas[i] = new Productor(2, 1, 2, semExclusionPantallas, semProduccionPantallas, semEnsamblajePantallas);
                        productoresPantallas[i].start();
                        numProductoresPantallas++;
                        break; 
                    }
                }
                break;
                
            case 3:
                
//            Contratar productor de joysticks
//            Recorrer el array de los productores de joysticks
//            Crear un nuevo productor en la última posición donde no hay un productor o hay uno que fue despedido
//            Inicializar un nuevo productor, arrancar el hilo y aumentar el contador de productores de joysticks
                
                for(int i = 0; i < productoresJoysticks.length; i++){
                    if(productoresJoysticks[i] == null || !productoresJoysticks[i].isIsContratado()){
                        productoresJoysticks[i] = new Productor(3, 2, 1, semExclusionJoysticks, semProduccionJoysticks, semEnsamblajeJoysticks);
                        productoresJoysticks[i].start();
                        numProductoresJoysticks++;
                        break; 
                    }
                }
                break;
                
            case 4:
                
//            Contratar productor de lectores de tarjetas SD
//            Recorrer el array de los productores de lectores de tarjetas SD
//            Crear un nuevo productor en la última posición donde no hay un productor o hay uno que fue despedido
//            Inicializar un nuevo productor, arrancar el hilo y aumentar el contador de productores de lectores de tarjetas SD
                
                for(int i = 0; i < productoresTarjertasSD.length; i++){
                    if(productoresTarjertasSD[i] == null || !productoresTarjertasSD[i].isIsContratado()){
                        productoresTarjertasSD[i] = new Productor(4, 3, 1, semExclusionTarjetasSD, semProduccionTarjetasSD, semEnsamblajeTarjetasSD);
                        productoresTarjertasSD[i].start();
                        numProductoresTarjetasSD++;
                        break; 
                    }
                }
                break;
                
            case 5:
                
//            Contratar ensamblador
//            Recorrer el array de los ensambladores
//            Crear un nuevo ensamblador en la última posición donde no hay un ensamblador o hay uno que fue despedido
//            Inicializar un nuevo ensamblador, arrancar el hilo y aumentar el contador de ensambladores
                
                for(int i = 0; i < ensambladores.length; i++){
                    if(ensambladores[i] == null || !ensambladores[i].isIsContratado()){
                        ensambladores[i] = new Ensamblador(semExclusionBotones, semExclusionPantallas, semExclusionJoysticks, semExclusionTarjetasSD, semProduccionBotones, semProduccionPantallas, semProduccionJoysticks, semProduccionTarjetasSD, semEnsamblajeBotones, semEnsamblajePantallas, semEnsamblajeJoysticks, semEnsamblajeTarjetasSD, semGerente);
                        ensambladores[i].start();
                        numEnsambladores++;
                        break; 
                    }
                }
                break;
                
//                En caso error, mostrar un mensaje de error
            default:
                JOptionPane.showMessageDialog(null, "Error, al contratar.", "ERROR DE CONTRATACIÓN", JOptionPane.ERROR_MESSAGE);

        }
    }
         
//    MÉTODO PARA DESPEDIR UN PRODUCTOR O ENSAMBLADOR
    public static void despedir(int tipo){
        
//        Condicional para despedir el tipo de productor o ensamblador correspondiente
        switch(tipo){
            
            case 1:
                
//            Despedir productor de botones
//            Recorrer el array de los productores de botones de atrás hacia adelante
//            Encontrar el primer productor activo
//            Cambiar el atributo isContratado del productor a false y reducir el contador de productores de botones
                
                for(int i = productoresBotones.length - 1; i >= 0; i--){
                    if(productoresBotones[i] != null){
                        if(productoresBotones[i].isIsContratado()){
                        productoresBotones[i].setIsContratado(false);
                        numProductoresBotones--;
                        break;
                        }
                    }
                }
                break;
                
            case 2:
                
//            Despedir productor de pantallas
//            Recorrer el array de los productores de pantallas de atrás hacia adelante
//            Encontrar el primer productor activo
//            Cambiar el atributo isContratado del productor a false y reducir el contador de productores de pantallas
                
                for(int i = productoresPantallas.length - 1; i >= 0; i--){
                    if(productoresPantallas[i] != null){
                        if(productoresPantallas[i].isIsContratado()){
                        productoresPantallas[i].setIsContratado(false);
                        numProductoresPantallas--;
                        break;
                        }
                    }
                }
                break;
                
            case 3:
                
//            Despedir productor de joysticks
//            Recorrer el array de los productores de joysticks de atrás hacia adelante
//            Encontrar el primer productor activo
//            Cambiar el atributo isContratado del productor a false y reducir el contador de productores de pantallas
                
                for(int i = productoresJoysticks.length - 1; i >= 0; i--){
                    if(productoresJoysticks[i] != null){
                        if(productoresJoysticks[i].isIsContratado()){
                        productoresJoysticks[i].setIsContratado(false);
                        numProductoresJoysticks--;
                        break;
                            
                        }
                    }
                }
                break;
                
            case 4:
                
//            Despedir productor de lectores de tarjetas SD
//            Recorrer el array de los productores de lectores de tarjetas SD de atrás hacia adelante
//            Encontrar el primer productor activo
//            Cambiar el atributo isContratado del productor a false y reducir el contador de productores de lectores de tarjetas SD
                
                for(int i = productoresTarjertasSD.length - 1; i >= 0; i--){
                    if(productoresTarjertasSD[i] != null){
                        if(productoresTarjertasSD[i].isIsContratado()){
                        productoresTarjertasSD[i].setIsContratado(false);
                        numProductoresTarjetasSD--;
                        break;
                        }
                    }
                }
                break;
                
            case 5:
                
//            Despedir ensamblador
//            Recorrer el array de los ensambladores de atrás hacia adelante
//            Encontrar el primer ensamblador activo
//            Cambiar el atributo isContratado del ensamblador a false y reducir el contador de ensambladores
                
                for(int i = ensambladores.length - 1; i >= 0; i--){
                    if(ensambladores[i] != null){
                        if(ensambladores[i].isIsContratado()){
                        ensambladores[i].setIsContratado(false);
                        numEnsambladores--;
                        break;
                            
                        }
                    }
                }
                break;
                
//                En caso de error, mostrar un mensaje
            default:
                JOptionPane.showMessageDialog(null, "Error, al despedir.", "ERROR DE DESPIDO", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
