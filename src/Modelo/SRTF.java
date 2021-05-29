/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import static Modelo.Main.listaProcesos;

/**
 *
 * @author Amaya
 */
public class SRTF {
    public static ArrayList<Proceso> procesos = listaProcesos;
    private static ArrayList<Proceso> colaProcesos = null;
    private static int totalCountTime = totalCountTime();
    
    public static void SRTF(){
        clock(); //All time needed to execute all duration time process
        
        //printTable();
    }
    
    public static void printTable(){
        System.out.println("Proceso \tDuracion \tLlegada \tPrioridad \ttEspera \ttTotal");
        for (int i=0; i < procesos.size(); i++ ){
            System.out.println(procesos.get(i));    
        }
    }
    
    private static void clock(){
        for(int i= 0; i< totalCountTime; i++){
            int arrived = isArrived(i);
            if(arrived != -1)
                System.out.println(arrived);
                //despachador(arrived);
            
            if(colaProcesos != null){
                System.out.println("Actualizamos contadores totales y espera, respectivamente");
            }
        }
    }
    
    private static int isArrived(int time){
        for(int i=0; i<procesos.size(); i++){
            if(time ==  procesos.get(i).getLlegada()){
                return i;
            }
        }
        return -1;
    }
    
    
    
    private static int totalCountTime(){
        int cont = 0;
        for (int i=0; i < procesos.size(); i++ ){
            //Suma total de las duraciones de los procesos
            cont += procesos.get(i).getDuracion();
        }
        cont += 1;
        return cont;
    }
    
    
}
