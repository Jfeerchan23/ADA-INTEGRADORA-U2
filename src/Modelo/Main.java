/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import java.util.Scanner;

import static Modelo.Prioridad.planificacionPrioridad;
import static Modelo.Prioridad.planificacionPrioridadDerecho;
import static Modelo.SRTF.SRTF;
import static Modelo.SRTFPreferente.SRTFPreferente;

public class Main {
    //LISTA madre/origen/base SE REINICIA CON CADA ITERACIÖN DEL SWITCH
    static ArrayList<Proceso> listaProcesos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        inicializar();

        do {
            System.out.println("¿Qué función desea ejecutar?");
            System.out.println("[0]: Salir \n[1]: FCFS \n[2]: SJF \n[3]: SRTF \n[4]: PRIORIDAD \n[5]: RR \n[6]: SRTF con Derecho Preferente \n[7]: PRIORIDAD con Derecho Preferente");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println("FCFS");
                    break;
                case 2:
                    System.out.println("SJF");
                    SJF.calculateShortestJobFirst(new ArrayList<>(listaProcesos));
                    break;
                case 3:
                    //Algoritmo SRTF (Shortest Remaining Time First)
                    SRTF();
                    break;
                case 4:
                    //Planificación por prioridad 
                    planificacionPrioridad();
                    break;
                case 5:
                    System.out.println("RR");
                    System.out.println("Quantum: ");
                    int quantum = sc.nextInt();
                    RR.calculateRoundRobin(new ArrayList<>(listaProcesos), quantum);
                    break;
                case 6:
                    SRTFPreferente();
                    break;
                case 7:
                    //Planificación por prioridad con derecho preferente
                    planificacionPrioridadDerecho();
                    break;
            }
            //PUEDEN HACER REFERENCIA A ESTA MISMA LISTA "listaProcesos"; SE REINICIA
            resetList();
        } while (option != 0);
    }

    private static void inicializar() {
        //(#Proceso, Duración, Llegada, Prioridad)
        listaProcesos.add(new Proceso("P1", 14, 8, 5));
        listaProcesos.add(new Proceso("P2", 22, 12, 2));
        listaProcesos.add(new Proceso("P3", 8, 0, 8));
        listaProcesos.add(new Proceso("P4", 16, 6, 5));
        listaProcesos.add(new Proceso("P5", 26, 24, 7));
        listaProcesos.add(new Proceso("P6", 24, 16, 9));
        listaProcesos.add(new Proceso("P7", 12, 20, 4));
        listaProcesos.add(new Proceso("P8", 18, 22, 8));
    }

    private static void resetList() {
        listaProcesos.clear();
        inicializar();
    }
}
