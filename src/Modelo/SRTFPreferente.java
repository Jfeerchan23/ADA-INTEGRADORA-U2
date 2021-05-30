/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

import static Modelo.Main.listaProcesos;

/**
 * @author Amaya
 */
public class SRTFPreferente {
    public static ArrayList<Proceso> process = listaProcesos;
    private static ArrayList<Proceso> queueProcess;
    private static Proceso current = null;
    private static final int totalCountTime = totalCountTime();
    private static int durationCount;

    public static void SRTFPreferente() {
        queueProcess = new ArrayList<>();
        clock(); //All time needed to execute all duration time process
        //printTable(false);
        finalSRTF();
        printTable(true);
    }

    private static void clock() {
        //Saber si hubo cambió y asignar valores de contador comodín
        boolean change = false;
        durationCount = 0;

        for (int i = 0; i < totalCountTime; i++) {
            int arrived = isArrived(i);
            if (arrived != -1) // -1: not found; other should be the index
                change = despachador(arrived); //TRUE: current = newProcess; FALSE: current = Still oldProcess
            if (!queueProcess.isEmpty()) { //If not empty() => has (1..*) process
                boolean finished = isFinished(durationCount);
                if (change || finished) { //change || some process has already finished
                    updateCounts(i);
                    //Se añade a su respectivo campo (Tespera o Ttotal). Por cambio o finalización
                    if (finished)
                        newProcess(-1);
                    else
                        newProcess(arrived);

                    //Reset values
                    durationCount = 0;
                    change = false;
                }
                durationCount++;
            }
        }
    }

    private static void newProcess(int idx) {
        if (idx >= 0) { // idx != -1
            //We know that index is in QueueProcess, bc of isArrived() function
            current = process.get(idx);
        } else {
            int idxToDelete = findIndex();
            queueProcess.remove(idxToDelete);
            int lessDuration = -1;
            Proceso lessProcess = null;
            for (Proceso pro : queueProcess) {
                //-1 as wildcard OR (duration - totalTime)  {totalTime == timeThatHadHadUsedTheProcess }
                if (lessDuration == -1 || (pro.getDuracion() - pro.gettTotal()) < lessDuration) {
                    lessProcess = pro;
                    lessDuration = pro.getDuracion() - pro.gettTotal();
                    current = pro;
                } else if ((pro.getDuracion() - pro.gettTotal()) == lessDuration) {
                    if (pro.getPrioridad() > lessProcess.getPrioridad()) {
                        lessProcess = pro;
                        lessDuration = pro.getDuracion() - pro.gettTotal();
                        current = pro;
                    }

                }
            }
        }
    }

    private static boolean isFinished(int count) {
        boolean res = false;
        if (0 == (current.getDuracion() - (count + current.gettTotal())))
            res = true;
        return res;
    }

    private static int findIndex() {
        for (int i = 0; i < queueProcess.size(); i++) {
            if (queueProcess.get(i) == current)
                return i;
        }
        return -1;
    }

    private static void updateCounts(int iPosTime) {
        int countComodin = 0;
        for (int i = 0; i < queueProcess.size(); i++) {
            countComodin = queueProcess.get(i).gettTotal();
            if (queueProcess.get(i) == current) {
                queueProcess.get(i).settTotal(countComodin + durationCount);
            } else {
                int iPosArrived = queueProcess.get(i).getLlegada();
                int waitTime = (iPosTime - iPosArrived) - countComodin;
                queueProcess.get(i).settEspera(waitTime);
            }
        }
    }

    private static int isArrived(int time) {
        for (int i = 0; i < process.size(); i++) {
            if (time == process.get(i).getLlegada()) {
                return i;
            }
        }
        return -1;
    }

    private static boolean despachador(int indexNewProcess) { //Compare (current vs new) process
        boolean res = false;

        if (queueProcess.isEmpty()) {
            queueProcess.add(process.get(indexNewProcess)); //Added to Queue if empty
            current = process.get(indexNewProcess);         //Update current with first process
        } else {
            //For current.duration and newProcess.duration, compare and execute SRTF 
            //durationCount just in case it hasn't had updated             >= To use "Con derecho preferente" NORMAL SERÍA >
            if (current.getDuracion() - (durationCount + current.gettTotal()) >= process.get(indexNewProcess).getDuracion()) {
                res = true;
            }
            //Add to queue. It will execute after currente finish
            queueProcess.add(process.get(indexNewProcess));
        }
        return res; //If TRUE: current = new process; FALSE: current = Still old process
    }

    private static int totalCountTime() {
        int cont = 0;
        for (int i = 0; i < process.size(); i++) {
            //Suma total de las duraciones de los procesos
            cont += process.get(i).getDuracion();
        }
        cont += 2;
        return cont;
    }

    public static void printTable(boolean full) {
        System.out.println("PLANIFICACIÓN POR SRTF (Shortest Remaining Time First)");
        System.out.println("Proceso \tDuracion \tLlegada \tPrioridad \ttEspera \ttTotal");
        for (int i = 0; i < process.size(); i++) {
            System.out.println(process.get(i));
        }
        if (full) {
            float promedioEspera, promedioTotal;
            int total = 0, espera = 0;
            for (Proceso pro : process) {
                total += pro.gettTotal();
                espera += pro.gettEspera();
            }
            promedioEspera = (float) espera / process.size();
            promedioTotal = (float) total / process.size();
            System.out.println("\t\t\t\t\t\tTotales: \t" + espera + " \t\t" + total);
            System.out.println("\t\t\t\t\t\tPromedios: \t" + promedioEspera + " \t\t" + promedioTotal);
        }
    }

    private static void finalSRTF() {
        int total;
        int espera;
        for (Proceso pro : process) {
            total = pro.gettTotal();
            espera = pro.gettEspera();
            pro.settTotal(total + espera);
        }
    }
}
