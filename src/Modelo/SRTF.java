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
        printTable();
        
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
            System.out.println("TIEMPO: " +i);
            int arrived = isArrived(i);
            if(arrived != -1) // -1: not found; other should be the index
                change = despachador(arrived); //TRUE: current = newProcess; FALSE: current = Still oldProcess
            if(!queueProcess.isEmpty()){ //If not empty() => has (1..*) process
                boolean finished = isFinished(durationCount);
                if(change || finished){ //change || some process has already finished
                    updateCounts(i);
                    System.out.println(durationCount);
                    //Se añade a su respectivo campo (Tespera o Ttotal). Por cambio o finalización
                    if(finished){
                        System.out.println("Buscar nuevo proceso más chico()");
                        newProcess(-1);
                    } else{
                        System.out.println("Cambio de current desde indice: " + arrived);
                        newProcess(arrived);
                    }
                    //Reset values
                    durationCount = 0;
                    change = false;
                }
                System.out.println("Contador: " + durationCount);
                durationCount++;
                System.out.println("-------- Current ------------------------------------");
                System.out.println("Proceso \tDuracion \tLlegada \tPrioridad \ttEspera \ttTotal");
                System.out.println(current);
            }
            System.out.println("##########################################################################################");
        }
    }
    
    private static void newProcess(int idx){
        System.out.println("------------ Current BF change ------------------------------");
        System.out.println("Proceso \tDuracion \tLlegada \tPrioridad \ttEspera \ttTotal");
        System.out.println(current);
        System.out.println("---------------- "+idx+" ------------------------------");
        if (idx >= 0){ // idx != -1
            //We know that index is in QueueProcess, bc of isArrived() function
            current = process.get(idx);
            for (Proceso pro : queueProcess) {
                System.out.println(pro);
            }
        } else {
            int idxToDelete = findIndex();
            queueProcess.remove(idxToDelete);
            int lessDuration = -1;
            for (Proceso pro : queueProcess) {
                //-1 as wildcard OR (duration - totalTime)  {totalTime == timeThatHadHadUsedTheProcess }
                if(lessDuration == -1 || (pro.getDuracion() - pro.gettTotal()) < lessDuration){
                    lessDuration = pro.getDuracion();
                    current = pro;
                }
                System.out.println(pro);
            }
            
        }
    }
    
    private static boolean isFinished(int count){
        boolean res = false;
            if(0==(current.getDuracion() - current.gettTotal()) || 0==(current.getDuracion() - count))
                res = true;
        return res;
    }
    
    private static int findIndex(){
        for(int i=0; i<queueProcess.size(); i++){
            System.out.println("i: "+i);
            if(queueProcess.get(i) == current){
                System.out.println("Returned: "+i);
                return i;
            }
        }
        return -1;
    }
    
    private static void updateCounts(int iPosTime){
        int countComodin = 0;
        for (int i = 0; i<queueProcess.size(); i++){
            countComodin = queueProcess.get(i).gettTotal();
            if(queueProcess.get(i) ==  current){
                System.out.println("Suma a TOTAL de current " + queueProcess.get(i).getProceso());
                queueProcess.get(i).settTotal(countComodin + durationCount);
                System.out.println(countComodin + durationCount);
            }else{
                System.out.println("Suma Espera de los demás " + queueProcess.get(i).getProceso() );
                int iPosArrived = queueProcess.get(i).getLlegada();
                int waitTime = (iPosTime - iPosArrived) - countComodin;
                queueProcess.get(i).settEspera(waitTime);
                System.out.println(waitTime);
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
            System.out.println("###___ primero ___###");
            //res = true;
        } else {
            //For current.duration and newProcess.duration, compare and execute SRTF 
            //durationCount just in case it hasn't had updated
            if(current.getDuracion() - (durationCount+current.gettTotal()) > process.get(indexNewProcess).getDuracion() ){
                System.out.println("###___ current > new ___###");
                res = true;
            } else {
                //Add to queue. It will execute after currente finish
                queueProcess.add(process.get(indexNewProcess));
                System.out.println("###___ A la cola ___###");
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
