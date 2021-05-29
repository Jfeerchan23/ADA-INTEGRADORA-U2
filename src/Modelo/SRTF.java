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
    public static ArrayList<Proceso> process = listaProcesos;
    private static ArrayList<Proceso> queueProcess = new ArrayList<Proceso>();
    private static Proceso current = null;
    private static int totalCountTime = totalCountTime();
    
    public static void SRTF(){
        clock(); //All time needed to execute all duration time process
        
        //printTable();
    }
    
    public static void printTable(){
        System.out.println("Proceso \tDuracion \tLlegada \tPrioridad \ttEspera \ttTotal");
        for (int i=0; i < process.size(); i++ ){
            System.out.println(process.get(i));    
        }
    }
    
    private static void clock(){
        for(int i= 0; i< totalCountTime; i++){
            int arrived = isArrived(i);
            if(arrived != -1)
                despachador(arrived);
            if(!queueProcess.isEmpty()){
                System.out.println("Actualizamos contadores totales y espera, respectivamente");
                System.out.println("SI está en cola se suma Tespera SINO a TTOTAL");
                System.out.println("Le podemos ir sumando de 1 en 1, o desde despachador crear una función que devuelva un boolean y haya un contador");
                System.out.println("que se le añada a su respectivo campo (Tespera o Ttotal) si hubiera un cambio entre current y new process");
            }
        }
    }
    
    private static int isArrived(int time){
        for(int i=0; i<process.size(); i++){
            if(time ==  process.get(i).getLlegada()){
                return i;
            }
        }
        return -1;
    }
    
    private static void despachador(int indexNewProcess){ //Compare (current vs new) process
        if(queueProcess.isEmpty()){
            //Added to Queue if empty
            queueProcess.add(process.get(indexNewProcess));
            //Update current with first process
            current = process.get(indexNewProcess);
        } else {
            //For current.duration and newProcess.duration, compare and execute SRTF 
            if(current.getDuracion() > process.get(indexNewProcess).getDuracion() ){
                System.out.println("Cambio de current");
                System.out.println("Old Process: "+current.getProceso()+" New Process: "+process.get(indexNewProcess).getProceso());
            } else {
                //Add to queue. It will execute after currente finish
                queueProcess.add(process.get(indexNewProcess));
            }
        }
    }
    
    private static int totalCountTime(){
        int cont = 0;
        for (int i=0; i < process.size(); i++ ){
            //Suma total de las duraciones de los procesos
            cont += process.get(i).getDuracion();
        }
        cont += 1;
        return cont;
    }
    
    /*
    
    */
    
}
