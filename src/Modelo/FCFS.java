/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Alrox
 */

import java.util.Scanner;

public class FCFS {
    String name;
    double arrivetime;  // Hora de llegada del proceso
    double servicetime;  // Duración del tiempo de ejecución del proceso 
    double starttime; // Hora de inicio del proceso de ejecución
    double finishtime; // Tiempo de finalización de la ejecución del proceso
    double zztime; // Tiempo de respuesta
    double dqzztime; // Tiempo de respuesta ponderado
    public FCFS(){ }
    public FCFS(String name, double arrivetime, double servicetime) {
        this.name = name;
        this.arrivetime = arrivetime;
        this.servicetime = servicetime;
    }

     public static void planificacionFCFS(){
             System.out.println("=============== Algoritmo de programación por orden de llegada ========================");
       
        FCFS[] p = new FCFS[8];
        p[0]=new FCFS("P1",13,8);
        p[1]=new FCFS("P2",12,22);
        p[2]=new FCFS("P3",0,8);
        p[3]=new FCFS("P4",6,16);
        p[4]=new FCFS("P5",24,26);
        p[5]=new FCFS("P6",16,24);
        p[6]=new FCFS("P7",20,12);
        p[7]=new FCFS("P8",22,18);
        
        
        
        
        // Crear objeto de matriz de proceso
   
       
        OS_FCFS(p); // Llamar al algoritmo por orden de llegada
     
     }
    
    
    // Algoritmo por orden de llegada
    private static void OS_FCFS(FCFS[] p) {
        sort(p); //Ordenar
        run(p); // Ejecuta el proceso
        print(p); // UI

        double Attime=0 ,AQttime = 0;
        for (int k=0; k<p.length;k++){
            Attime += p[k].zztime;
            AQttime += p[k].dqzztime;
        }
        Attime = Attime/p.length;
        AQttime = AQttime/p.length;

        System.out.println("El tiempo TOTAL Promedio:"+Attime);
        //  System.out.printf("%.3f\n",AQttime);
        System.out.println("El tiempo de ESPERA Promedio:"+AQttime);
    }

    // Algoritmo de clasificación (método de clasificación de burbujas)
    public static void sort(FCFS[] p){
        for (int i=0;i<p.length;i++){
            for (int j=i+1;j<p.length;j++){
                if (p[i].arrivetime>p[j].arrivetime){
                    FCFS temp;
                    temp = p[i];
                    p[i] = p[j];
                    p[j]= temp;
                }
            }
        }
    }


    // Ejecución del proceso
    private static void run(FCFS[] p) {
        for(int k=0; k<p.length;k++){
            if (k==0){
                p[k].starttime = p[k].arrivetime;
                p[k].finishtime = p[k].arrivetime+p[k].servicetime;
            }else{
                p[k].starttime = p[k-1].finishtime;
                p[k].finishtime = p[k-1].finishtime+p[k].servicetime;
            }
        }
        for (int k=0; k<p.length;k++){
            p[k].zztime     = p[k].finishtime - p[k].arrivetime;  // Calcula el tiempo de respuesta del proceso | El TOTAL de ejecución
            p[k].dqzztime   = p[k].starttime - p[k].arrivetime;  // Calcula el tiempo de respuesta correcto del proceso
        }
    }


    // Resultado echo
    private static void print(FCFS[] p) {
        System.out.println("Llame al algoritmo por orden de llegada y luego la secuencia de operación del proceso es:");
        System.out.print(p[0].name);
        for (int k=1;k<p.length;k++){
            System.out.print("-->"+p[k].name);
        }
        System.out.println("");
        System.out.println("Información de programación específica:");
        System.out.println("Proceso Llegada Servicio  Inicio   Finalización   Total   Espera");
        for(int k =0;k<p.length;k++){
            System.out.printf("%4s",p[k].name);
            System.out.printf("%10.3f",p[k].arrivetime);
            System.out.printf("%10.3f",p[k].servicetime);
            System.out.printf("%10.3f",p[k].starttime);
            System.out.printf("%10.3f",p[k].finishtime);
            System.out.printf("%10.3f",p[k].zztime);
            System.out.printf("%10.3f\n",p[k].dqzztime);
        }
    }



}
