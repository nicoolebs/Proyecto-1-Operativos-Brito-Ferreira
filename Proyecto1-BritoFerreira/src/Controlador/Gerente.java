package Controlador;

import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

//CLASE DEL GERENTE
public class Gerente extends Thread{
    
//    VARIABLES
    private Semaphore semGerente, semJefe; //Semáforos del jefe y del gerente

//    CONSTRUCTOR DEL GERENTE
    public Gerente(Semaphore semGerente, Semaphore semJefe) {
        this.semGerente = semGerente;
        this.semJefe = semJefe;
    }
    
//    MÉTODO RUN DEL GERENTE
    @Override
    public void run(){
        
//        El gerente siempre está trabajando
        while(true){
            
            //Se cambia el estado del gerente a esperando
            //Se toma en exclusividad la variable que modifica el jefe
            //Se cambia el estado del gerente a verificando
            //Si aun no es el día del despliegue...
            //      Se libera la exclusividad de la variable que modifica el jefe
            //      Se cambia el estado del gerente a durmiendo
            //      Se duerme al gerente por 2 horas
            //Si es el día del despligue...
            //      Se toma en exclusividad la variable que modifica el gerente
            //      Se cambia el estado del gerente a desplegando
            //      Se guarda la cantidad de consolas ensambladas en el despliegue actual
            //      Se reinicia el contador del almacen de consolas
            //      Se reinicia el contador de días para el próximo despliegue
            //      Se libera la exclusividad de la variable que modifica el gerente
            //      Se libera la exclusividad de la varaible que modifica el jefe
            
            try {
                    Almacen.estadoGerente = "Esperando al Jefe";
                
                    this.semJefe.acquire();
                    
                    Almacen.estadoGerente = "Verificando días";
                    
                    if(Almacen.diasRestantesDespliegue != 0){
                        
                        this.semJefe.release();
                        Almacen.estadoGerente = "Durmiendo la siesta";
                        Thread.sleep(Almacen.duracionDia * 2 / 24);
                        
                    }else{
                        
                            this.semGerente.acquire();
                                Almacen.estadoGerente = "Desplegando New 15SD XL";
                                Almacen.cantidadConsolasUltimoDespliegue = Almacen.cantidadConsolas;
                                Almacen.cantidadConsolas = 0;
                                Almacen.diasRestantesDespliegue = Almacen.diasDespliegue;                    
                            this.semGerente.release();
                        this.semJefe.release();
                        
                    }
                
//              En caso de que ocurra una excepción, se muestra un mensaje de error
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error, el gerente está fallando.", "ERROR DEL GERENTE", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
    
}
