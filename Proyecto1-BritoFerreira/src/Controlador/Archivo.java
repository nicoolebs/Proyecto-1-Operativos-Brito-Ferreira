/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JOptionPane;

/**
 *
 * @author Giselle Ferreira
 */
public class Archivo {

    //Metodo para abrir el archivo contentivo del resumen
    public static boolean leerArchivo(){
        
        boolean valido = true;

        try {//Intento leer el archivo

//            Se crea el lector del archivo txt con los parámetros iniciales
            BufferedReader bf = new BufferedReader(new FileReader(new File("ParametrosIniciales.txt")));
            
//            Se leen cada uno de los parámetros del archivo
            Almacen.duracionDia = Integer.parseInt(bf.readLine().split("=")[1]) * 1000;
            Almacen.diasDespacho = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.capacidadBotones = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.capacidadPantallas = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.capacidadJoysticks = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.capacidadTarjetasSD = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.productoresInicialesBotones = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.productoresInicialesPantallas = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.productoresInicialesJoysticks = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.productoresInicialesTarjetasSD = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.productoresMaximosBotones = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.productoresMaximosPantallas = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.productoresMaximosJoysticks = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.productoresMaximosTarjetasSD = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.ensambladoresIniciales = Integer.parseInt(bf.readLine().split("=")[1]);
            Almacen.ensambladoresMaximos = Integer.parseInt(bf.readLine().split("=")[1]);
            
//            Validaciones del archivo
            if(Almacen.duracionDia <= 0){
                JOptionPane.showMessageDialog(null, "Error, la duración del día no puede ser menor o igual a 0 segundos.", "ERROR DE DURACIÓN DEL DÍA", JOptionPane.ERROR_MESSAGE); 
                valido = false;
            }
            if(Almacen.diasDespacho <= 0){
                JOptionPane.showMessageDialog(null, "Error, los días para el despacho no pueden ser menores a 0.", "ERROR DE DÍAS DE DESPACHO", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            if(Almacen.capacidadBotones <= 0 || Almacen.capacidadPantallas <= 0 || Almacen.capacidadJoysticks <= 0 || Almacen.capacidadTarjetasSD <= 0){
                JOptionPane.showMessageDialog(null, "Error, no se puede leer el archivo de parámetros iniciales.", "ERROR DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            if((Almacen.productoresInicialesBotones > Almacen.productoresMaximosBotones) || (Almacen.productoresInicialesPantallas > Almacen.productoresInicialesPantallas) || (Almacen.productoresInicialesJoysticks > Almacen.productoresMaximosJoysticks) || (Almacen.productoresInicialesTarjetasSD > Almacen.productoresMaximosTarjetasSD)){
               JOptionPane.showMessageDialog(null, "Error, no se puede leer el archivo de parámetros iniciales.", "ERROR DE ARCHIVO", JOptionPane.ERROR_MESSAGE); 
                valido = false;
            }
            if(Almacen.productoresInicialesBotones < 0 || Almacen.productoresInicialesJoysticks < 0 || Almacen.productoresInicialesPantallas < 0 || Almacen.productoresInicialesTarjetasSD < 0){
                JOptionPane.showMessageDialog(null, "Error, no se puede leer el archivo de parámetros iniciales.", "ERROR DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            if(Almacen.productoresMaximosBotones <= 0 || Almacen.productoresMaximosJoysticks <= 0 || Almacen.productoresMaximosPantallas <= 0 || Almacen.productoresMaximosTarjetasSD <= 0){
                JOptionPane.showMessageDialog(null, "Error, no se puede leer el archivo de parámetros iniciales.", "ERROR DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            if(Almacen.ensambladoresIniciales < 0){
                JOptionPane.showMessageDialog(null, "Error, no se puede leer el archivo de parámetros iniciales.", "ERROR DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            if(Almacen.ensambladoresMaximos <= 0){
                JOptionPane.showMessageDialog(null, "Error, no se puede leer el archivo de parámetros iniciales.", "ERROR DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            if(Almacen.ensambladoresIniciales > Almacen.ensambladoresMaximos){
                JOptionPane.showMessageDialog(null, "Error, no se puede leer el archivo de parámetros iniciales.", "ERROR DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            
//            Cerrar el archivo
            bf.close();
            
                      
        } catch (Exception e) {
                            
            //Si no se puede leer el archivo txt arrojar mensaje de error
            JOptionPane.showMessageDialog(null, "Error, no se puede leer el archivo de parámetros iniciales.", "ERROR DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
                    
        }

//        Retornar si los datos del archivo son válidos
        return valido;
                    
    }
    
}
