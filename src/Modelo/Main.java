/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import static Modelo.Prioridad.PRIORIDAD;
import static Modelo.SRTF.SRTF;
import java.util.ArrayList;
public class Main {

    static ArrayList<Proceso> listaProcesos = new ArrayList();
    
    public static void main(String[] args) {
        inicializar();
        
        //Algoritmo Planificación por prioridad
        //PRIORIDAD();
        //Algoritmo SRTF (Shortest Remaining Time First)
        SRTF();
         
         
     
    }
private static void inicializar(){
    //(#Proceso, Duración, Llegada, Prioridad)
    listaProcesos.add(new Proceso("P1",14,8,5));
    listaProcesos.add(new Proceso("P2",22,12,2));
    listaProcesos.add(new Proceso("P3",8,0,8));
    listaProcesos.add(new Proceso("P4",16,6,5));
    listaProcesos.add(new Proceso("P5",26,24,7));
    listaProcesos.add(new Proceso("P6",24,16,9));
    listaProcesos.add(new Proceso("P7",12,20,4));
    listaProcesos.add(new Proceso("P8",18,22,8));
}

  
  
  
  
}
