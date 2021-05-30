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
    private static int durationCount;
    
    public static void SRTF(){
        clock(); //All time needed to execute all duration time process
        //Sumar ESPERA y TOTAL
        printTable();
    }
    
    public static void printTable(){
        System.out.println("PLANIFICACIÓN POR SRTF (Shortest Remaining Time First)");
        System.out.println("Proceso \tDuracion \tLlegada \tPrioridad \ttEspera \ttTotal");
        for (int i=0; i < process.size(); i++ ){
            System.out.println(process.get(i));    
        }
    }
    
    private static void clock(){
        //Saber si hubo cambió y asignar valores de contador comodín
        boolean change = false;
        durationCount = 0;
        
        for(int i= 0; i< totalCountTime; i++){
            int arrived = isArrived(i);
            if(arrived != -1) // -1: not found; other should be the index
                change = despachador(arrived); //TRUE: current = newProcess; FALSE: current = Still oldProcess
            if(!queueProcess.isEmpty()){ //If not empty() => has (1..*) process
                boolean finished = isFinished(durationCount);
                if(change || finished){ //change || some process has already finished
                    updateCounts(durationCount, i);
                    System.out.println(durationCount);
                    //System.out.println("que se le añada a su respectivo campo (Tespera o Ttotal) si hubiera un cambio entre current y new process");
                    if(finished){
                        System.out.println("Buscar nuevo proceso más chico()");
                    } else{
                        System.out.println("Cambio de current desde indice: " + arrived);
                    }
                    //Reset values
                    durationCount = 0;
                    change = false;
                }
                System.out.println("Contador: " + durationCount);
                durationCount++;
            }
            System.out.println("Contador Afuera++: " + durationCount);
            System.out.println("=#=#=#=#==#=#=#==#=#=#==#=#=#=#=#=#=#=#=");
        }
    }
    
    private static boolean isFinished(int count){
        boolean res = false;
            if(0==(current.getDuracion() - current.gettTotal()) || 0==(current.getDuracion() - count))
                res = true;
        return res;
    }
    
    private static void updateCounts(int count, int iPosTime){
        int countComodin = 0;
        for (int i = 0; i<queueProcess.size(); i++){
            countComodin = queueProcess.get(i).gettTotal();
            if(queueProcess.get(i) ==  current){
                System.out.println("Suma a TOTAL de current");
                queueProcess.get(i).settTotal(countComodin + count);
                System.out.println(countComodin + count);
            }else{
                System.out.println("Suma Espera de los demás");
                int iPosArrived = queueProcess.get(i).getLlegada();
                int waitTime = (iPosTime - iPosArrived) - countComodin;
                queueProcess.get(i).settEspera(waitTime);
                System.out.println(waitTime);
                //System.out.println("TOTAL de otro: "+ queueProcess.get(i).gettEspera());
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
    
    private static boolean despachador(int indexNewProcess){ //Compare (current vs new) process
        boolean res = false;
        
        if(queueProcess.isEmpty()){
            queueProcess.add(process.get(indexNewProcess)); //Added to Queue if empty
            current = process.get(indexNewProcess);         //Update current with first process
            System.out.println("### primero ###");
            res = true;
        } else {
            //For current.duration and newProcess.duration, compare and execute SRTF 
            if(current.getDuracion() > process.get(indexNewProcess).getDuracion() ){
                System.out.println("### current > new ###");
                res = true;
            } else {
                //Add to queue. It will execute after currente finish
                queueProcess.add(process.get(indexNewProcess));
                System.out.println("### A la cola ###");
            }
        }
        return res; //If TRUE: current = new process; FALSE: current = Still old process
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
