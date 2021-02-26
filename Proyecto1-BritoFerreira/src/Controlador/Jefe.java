package Controlador;

import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

//CLASE DEL JEFE
public class Jefe extends Thread{
    
//    VARIABLES
    private Semaphore semJefe; //Semáforo del jefe

//    CONSTRUCTOR DEL JEFE
    public Jefe(Semaphore semJefe) {
        this.semJefe = semJefe;
    }
    
//    MÉTODO RUN PARA EL JEFE
    @Override
    public void run(){
        
//        El jefe va a estar trabajando siempre
        while(true){
            
            try {
                
                //Se actualiza el estado del jefe a esperando
                //Se entrega en exclusividad la variable que modifica  el jefe
                //Si aun quedan días para el despliegue...
                //Se cambia el estado del jefe a actualizando
                //Se duerme el jefe por las 6 horas que tarda cambiando el día
                //Se reduce un día para el despliegue
                //Se libera la exclusividad de la variable que modifica el jefe
                //Se cambia el estado del jefe a durmiendo
                //Se pone al jefe a dormir por las 18 horas que quedan del día
                
                Almacen.estadoJefe = "Esperando al Gerente";
                
                this.semJefe.acquire();
                
                    if(Almacen.diasRestantesDespliegue != 0){
                        Almacen.estadoJefe = "Actualizando días restantes";
                        Thread.sleep(Almacen.duracionDia * 6 / 24);
                        Almacen.diasRestantesDespliegue--;
                    }
                
                this.semJefe.release();
                
                    Almacen.estadoJefe = "Durmiendo la siesta";
                    Thread.sleep(Almacen.duracionDia * 18 / 24);
                
//              En caso de que ocurra una excepción, se muestra un mensaje de error
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error, el jefe está fallando.", "ERROR DEL JEFE", JOptionPane.ERROR_MESSAGE);
            }
            
        }
        
    }
}
