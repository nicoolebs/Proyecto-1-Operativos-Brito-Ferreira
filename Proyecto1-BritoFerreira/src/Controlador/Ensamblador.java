package Controlador;

import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

//CLASE DE ENSAMBLADOR DE LAS CONSOLAS
public class Ensamblador extends Thread{
    
//    VARIABLES
    private Semaphore semExclusionBotones, semExclusionPantallas, semExclusionJoysticks, semExclusionTarjetasSD; //Semáforos de exclusividad de los almacenes
    private Semaphore semProduccionBotones, semProduccionPantallas, semProduccionJoysticks, semProduccionTarjetasSD; //Semáforos de los productores
    private Semaphore semEnsamblajeBotones, semEnsamblajePantallas, semEnsamblajeJoysticks, semEnsamblajeTarjetasSD; //Semáforos de los ensambladores
    private Semaphore semGerente; //Semáforo del gerente
    private boolean isContratado; //Booleano para contratar o despedir ensambladores

//    CONSTRUCTOR DE LOS ENSAMBLADORES
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
    }
    
//   MÉTODO RUN DE ENSAMBLADORES 
    @Override
    public void run(){
        
//        El ensamblador va a ensamblar mientras esté contratado
        while(this.isContratado){
            
            try {
                
//                  Se agarran todas las piezas necesarias de los almacenes, reduciendo la capacidad de consumo
                this.semEnsamblajeBotones.acquire(5);
                this.semEnsamblajePantallas.acquire(2);
                this.semEnsamblajeJoysticks.acquire(2);
                this.semEnsamblajeTarjetasSD.acquire();
                
//                      Se entrega en exclusividad todas las variables de los almacenes
                    this.semExclusionBotones.acquire();
                    this.semExclusionPantallas.acquire();
                    this.semExclusionJoysticks.acquire();
                    this.semExclusionTarjetasSD.acquire();
                            
//                          Se saca de los almacenes todas las piezas
                        Almacen.cantidadBotones -= 5;
                        Almacen.cantidadPantallasNormales--;
                        Almacen.cantidadPantallasTactiles--;
                        Almacen.cantidadJoysticks -= 2;
                        Almacen.cantidadTarjetasSD--;
                            
//                      Se libera la exclusividad de todas las variables de los almacenes
                    this.semExclusionBotones.release();
                    this.semExclusionPantallas.release();
                    this.semExclusionJoysticks.release();
                    this.semExclusionTarjetasSD.release();
                
//                  Se aumenta la capacidad de producción de todos los almacenes
                this.semProduccionBotones.release(5);
                this.semProduccionPantallas.release(2);
                this.semProduccionJoysticks.release(2);
                this.semProduccionTarjetasSD.release();
                
//                  Luego se duerme el ensamblador para producir la consola
                Thread.sleep(Almacen.duracionDia);
                
//                  Se entrega en exclusividad la variable que lee el gerente
                this.semGerente.acquire();

//                      Se produce la consola y se agrega al almacen de consolas
                    Almacen.cantidadConsolas++;

//                  Se libera la exclusividad de la variable que lee el gerente
                this.semGerente.release();
                
//                En caso de que ocurra una excepción, se muestra un mensaje de error
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error, los ensambladores estan fallando.", "ERROR DE ENSAMBLAJE", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }

//    GETTERS Y SETTERS DE TODAS LAS VARIABLES
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

    public Semaphore getSemGerente() {
        return semGerente;
    }

    public void setSemGerente(Semaphore semGerente) {
        this.semGerente = semGerente;
    }

    public boolean isIsContratado() {
        return isContratado;
    }

    public void setIsContratado(boolean isContratado) {
        this.isContratado = isContratado;
    }
    
}
