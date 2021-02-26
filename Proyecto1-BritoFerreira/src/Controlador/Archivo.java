package Controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JOptionPane;

//CLASE PARA LEER EL ARCHIVO TXT CON LOS PARÁMETROS INICIALES
public class Archivo {

//      MÉTODO PARA LEER EL ARCHIVO TXT
    public static boolean leerArchivo(){
        
//        Booleano para saber si el archivo pasa por todas las validaciones
        boolean valido = true;

        try {

//            Se crea el lector del archivo txt con los parámetros iniciales
            BufferedReader bf = new BufferedReader(new FileReader(new File("ParametrosIniciales.txt")));
            
//            Se leen cada uno de los parámetros del archivo
            Almacen.duracionDia = Integer.parseInt(bf.readLine().split("=")[1]) * 1000;
            Almacen.diasDespliegue = Integer.parseInt(bf.readLine().split("=")[1]);
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
            
//              Validaciones del archivo

//              Validación si la duración del día es menos de 0 segundos
            if(Almacen.duracionDia <= 0){
                JOptionPane.showMessageDialog(null, "Error, la duración del día no puede ser menor o igual a 0 segundos.", "ERROR DE DURACIÓN DEL DÍA", JOptionPane.ERROR_MESSAGE); 
                valido = false;
            }
            
//            Validación si los días para el despliegue son negativos
            if(Almacen.diasDespliegue <= 0){
                JOptionPane.showMessageDialog(null, "Error, los días para el despacho no pueden ser menores a 0.", "ERROR DE DÍAS DE DESPACHO", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            
//            Validación de que las capacidades de los almacenes no sean negativas
            if(Almacen.capacidadBotones <= 0 || Almacen.capacidadPantallas <= 0 || Almacen.capacidadJoysticks <= 0 || Almacen.capacidadTarjetasSD <= 0){
                JOptionPane.showMessageDialog(null, "Error, la capacidad de los almacenes no puede ser menor a 0.", "ERROR DE CAPACIDAD DE ALMACENES", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            
//            Validación de que los productores iniciales no sean mayores a los productores máximos
            if((Almacen.productoresInicialesBotones > Almacen.productoresMaximosBotones) || (Almacen.productoresInicialesPantallas > Almacen.productoresInicialesPantallas) || (Almacen.productoresInicialesJoysticks > Almacen.productoresMaximosJoysticks) || (Almacen.productoresInicialesTarjetasSD > Almacen.productoresMaximosTarjetasSD)){
               JOptionPane.showMessageDialog(null, "Error, no puedes tener más productores de los permitidos.", "ERROR DE NÚMERO DE PRODUCTORES", JOptionPane.ERROR_MESSAGE); 
                valido = false;
            }
            
//            Validación de que los productores iniciales no sean negativos
            if(Almacen.productoresInicialesBotones < 0 || Almacen.productoresInicialesJoysticks < 0 || Almacen.productoresInicialesPantallas < 0 || Almacen.productoresInicialesTarjetasSD < 0){
                JOptionPane.showMessageDialog(null, "Error, la cantidad de productores iniciales no puede ser negativa.", "ERROR DE NÚMERO INICIAL DE PRODUCTORES", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            
//            Validación de que los productores máximos no sean negativos
            if(Almacen.productoresMaximosBotones <= 0 || Almacen.productoresMaximosJoysticks <= 0 || Almacen.productoresMaximosPantallas <= 0 || Almacen.productoresMaximosTarjetasSD <= 0){
                JOptionPane.showMessageDialog(null, "Error, la cantidad de productores máximos no puede ser negativa.", "ERROR DE NÚMERO DE PRODUCTORES MÁXIMOS", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            
//            Validación de que los ensambladores iniciales no pueden ser negativos
            if(Almacen.ensambladoresIniciales < 0){
                JOptionPane.showMessageDialog(null, "Error, la cantidad de ensambladores iniciales no puede ser negativa.", "ERROR DE NÚMERO DE ENSAMBLADORES INICIALES", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            
//            Validación de que los ensambladores máximos no pueden ser negativos
            if(Almacen.ensambladoresMaximos <= 0){
                JOptionPane.showMessageDialog(null, "Error, la cantidad de ensambladores máximos no puede ser negativa.", "ERROR DE NÚMERO DE ENSAMBLADORES MÁXIMOS", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            
//            Validación de que los ensambladores iniales no sean mayores que los ensambladores máximos
            if(Almacen.ensambladoresIniciales > Almacen.ensambladoresMaximos){
                JOptionPane.showMessageDialog(null, "Error, no se pueden tener más ensambladores de los permitidos.", "ERROR DE NÚMERO DE ENSAMBLADORES", JOptionPane.ERROR_MESSAGE);
                valido = false;
            }
            
//            Cerrar el archivo
            bf.close();
            
                      
        } catch (Exception e) {
                            
            //Si no se puede leer el archivo txt arrojar mensaje de error
            JOptionPane.showMessageDialog(null, "Error, no se puede leer el archivo de parámetros iniciales. Revise el contenido del mismo", "ERROR DE ARCHIVO", JOptionPane.ERROR_MESSAGE);
                    
        }

//        Retornar si los datos del archivo son válidos
        return valido;
                    
    }
    
}
