package Controlador;

import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

//CLASE PRODUCTOR QUE MANEJA TODOS LOS PRODUCTORES DE LOS DISTINTOS PRODUCTOS
public class Productor extends Thread {

//    VARIABLES
    private int tipoPieza; //Para saber el tipo de productor que esta ejecutándose
    private int tiempoProduccion; //Para saber el tiempo de producción del tipo de productor
    private int cantProduccion; //Para saber la cantidad piezas que produce el productor
    private boolean isContratado; //Para contratar o despedir al productor
    private Semaphore semExclusion, semProduccion, semEnsamblaje; //Semáforos para problema productor/consumidor

//    CONSTRUCTOR DEL PRODUCTOR
    public Productor(int tipoPieza, int tiempoProduccion, int cantProduccion, Semaphore semExclusion, Semaphore semProduccion, Semaphore semEnsamblaje) {
        this.tipoPieza = tipoPieza;
        this.tiempoProduccion = tiempoProduccion;
        this.cantProduccion = cantProduccion;
        this.semExclusion = semExclusion;
        this.semProduccion = semProduccion;
        this.semEnsamblaje = semEnsamblaje;
        this.isContratado = true;
    }

//    MÉTODO RUN DE PRODUCTORES
    @Override
    public void run() {

//        El productor funciona mientras no sea despedido
        while (isContratado) {

            try {

//                Los productores producen según el tipo de productor que sea
                switch (this.tipoPieza) {

                    //                    Producción de botones
                    case 1:
                        
                        //Se reduce capacidad de producción de botones
                        //Dormir para producir botones
                        //Asignar exclusividad de la variable del almacen de botones
                        //Aumentar cantidad producida de botones
                        //Liberar exclusividad de la variable del almacen de botones
                        //Se aumenta la capacidad de consumo de botones
                        
                        this.semProduccion.acquire(cantProduccion); 
                            Thread.sleep(Almacen.duracionDia * this.tiempoProduccion); 
                            this.semExclusion.acquire(); 
                                Almacen.cantidadBotones += 2; 
                            this.semExclusion.release(); 
                        this.semEnsamblaje.release(cantProduccion); 
                        break;

                    //                    Producción de pantallas
                    case 2:
                        
                        //Se reduce capacidad de producción de las dos pantallas
                        //Dormir para producir pantalla normal
                        //Asignar exclusividad de la variable del almacen de pantallas
                        //Aumentar cantidad producida de pantallas normales
                        //Liberar exclusividad de la variable del almacen de pantallas
                        //Dormir para producir pantalla táctil
                        //Asignar exclusividad de la variable del almacen de pantallas
                        //Aumentar cantidad producida de pantallas táctiles
                        //Liberar exclusividad de la variable del almacen de pantallas
                        //Se aumenta la capacidad de consumo de las dos pantallas
                        
                        this.semProduccion.acquire(cantProduccion); 
                            Thread.sleep(Almacen.duracionDia * this.tiempoProduccion); 
                            this.semExclusion.acquire();
                                Almacen.cantidadPantallasNormales++;
                            this.semExclusion.release();

                            Thread.sleep(Almacen.duracionDia * this.tiempoProduccion * 2); 
                            this.semExclusion.acquire();
                                Almacen.cantidadPantallasTactiles++;
                            this.semExclusion.release();
                        this.semEnsamblaje.release(cantProduccion);
                        break;

                    //                    Producción de joysticks    
                    case 3:
                        
                        //Se reduce capacidad de producción de joysticks
                        //Dormir para producir joysticks
                        //Asignar exclusividad de la variable del almacen de joysticks
                        //Aumentar cantidad producida de joysticks
                        //Liberar exclusividad de la variable del almacen de joysticks
                        //Se aumenta la capacidad de consumo de joysticks
                        
                        this.semProduccion.acquire(cantProduccion);
                            Thread.sleep(Almacen.duracionDia * this.tiempoProduccion);
                            this.semExclusion.acquire();
                                Almacen.cantidadJoysticks++;
                            this.semExclusion.release();
                        this.semEnsamblaje.release(cantProduccion);
                        break;

                    //                    Producción de lectores de tarjetas SD
                    case 4:
                        
                        //Se reduce capacidad de producción de lectores de tarjetas SD
                        //Dormir para producir lectores de tarjetas SD
                        //Asignar exclusividad de la variable del almacen de lectores de tarjetas SD
                        //Aumentar cantidad producida de lectores de tarjetas SD
                        //Liberar exclusividad de la variable del almacen de lectores de tarjetas SD
                        //Se aumenta la capacidad de consumo de lectores de tarjetas SD
                        
                        this.semProduccion.acquire(cantProduccion);
                            Thread.sleep(Almacen.duracionDia * this.tiempoProduccion);
                            this.semExclusion.acquire();
                                Almacen.cantidadTarjetasSD++;
                            this.semExclusion.release();
                        this.semEnsamblaje.release(cantProduccion);
                        break;

                }

//                En caso de que ocurra una excepción entonces se muestra un mensaje de error
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "Error, los productores estan fallando.", "ERROR DE PRODUCCIÓN", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

//    GETTERS Y SETTERS DE TODAS LAS VARIABLES
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

    public int getCantProduccion() {
        return cantProduccion;
    }

    public void setCantProduccion(int cantProduccion) {
        this.cantProduccion = cantProduccion;
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
