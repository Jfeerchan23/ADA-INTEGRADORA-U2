/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;
import static Modelo.Main.listaProcesos;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author fer_c
 */
public class Prioridad {
  static ArrayList<Proceso> Prioridad = new ArrayList();
public static void imprimir(ArrayList aux){
System.out.println("Proceso \tDuracion \tLlegada \tPrioridad \ttEspera \ttTotal");
     for(int i=0; i<aux.size();i++){

       System.out.println(aux.get(i).toString());
    }
}
public static int maximaLlegada(){
  int x=0;
  for(int i=0;i<listaProcesos.size();i++){
      if(x<listaProcesos.get(i).getLlegada()){
          x=listaProcesos.get(i).getLlegada();
      }
  }
    return x;

}
public static int buscar(ArrayList<Proceso> temp, int x, ArrayList<Proceso> PorPrioridad){

  int mayorPrioridad=0;
  int rep=0;
  int menorLlegada;
  int indice=0;
  for(int i=0;i<temp.size();i++){
      if(temp.get(i).getPrioridad()>mayorPrioridad){
          indice=i;
          mayorPrioridad=temp.get(i).getPrioridad();
      }
  }
  for(int i=0;i<temp.size();i++){
      if(temp.get(i).getPrioridad()==mayorPrioridad){
          rep++;
      }
  }
  if(rep==1){
      PorPrioridad.add(temp.get(indice));
      int llegada= PorPrioridad.get(PorPrioridad.size()-1).getLlegada();
      PorPrioridad.get(PorPrioridad.size()-1).settEspera(x-llegada);
      int duracion=PorPrioridad.get(PorPrioridad.size()-1).getDuracion();
      int espera=PorPrioridad.get(PorPrioridad.size()-1).gettEspera();
      PorPrioridad.get(PorPrioridad.size()-1).settTotal(duracion+espera);
      temp.remove(indice);
      x=x+duracion;
  }else{
      int menor=10000;
      for(int i=0;i<temp.size();i++){

          if(temp.get(i).getPrioridad()==mayorPrioridad){

           if(temp.get(i).getLlegada()<menor){
               menor=temp.get(i).getLlegada();
               indice=i;
           }
          }
      }
       PorPrioridad.add(temp.get(indice));
      int llegada= PorPrioridad.get(PorPrioridad.size()-1).getLlegada();
      PorPrioridad.get(PorPrioridad.size()-1).settEspera(x-llegada);
      int duracion=PorPrioridad.get(PorPrioridad.size()-1).getDuracion();
      int espera=PorPrioridad.get(PorPrioridad.size()-1).gettEspera();
      PorPrioridad.get(PorPrioridad.size()-1).settTotal(duracion+espera);
      temp.remove(indice);
      x=x+duracion;
  }

  return x;
}
public static void planificacionPrioridad(){
    inicializar();
  ArrayList<Proceso> lista= Prioridad;
 ArrayList<Proceso> PorPrioridad = new ArrayList();
 ArrayList<Proceso> temporal =  new ArrayList();
   int i=0;
    boolean primero=false;
    int y=0;


  while(i<=maximaLlegada()){


      for(int j=0;j<lista.size();j++){
          if(lista.get(j).getLlegada()==i){
              if(temporal.isEmpty()&&primero==false){
                  PorPrioridad.add(lista.get(j));
                  PorPrioridad.get(PorPrioridad.size()-1).settTotal(PorPrioridad.get(PorPrioridad.size()-1).getDuracion());
                  y=y+PorPrioridad.get(PorPrioridad.size()-1).getDuracion();
                  lista.remove(j);
                  primero=true;
              }else{

                 temporal.add(lista.get(j));
                 lista.remove(j);
                //Función para ver que proceso sigue
              }


          }
      }
      if(i==y){
          y=buscar(temporal,y,PorPrioridad);

      }

      i++;
  }


  while(!temporal.isEmpty()){

     y= buscar(temporal,y,PorPrioridad);
  }
Collections.sort(PorPrioridad, new Comparator<Proceso>() {
	
	public int compare(Proceso p1, Proceso p2) {
		return new String (p1.getProceso()).compareTo(new String(p2.getProceso()));
	}
});

System.out.println("\nPLANIFICACIÓN POR PRIORIDAD\n");
  imprimir(PorPrioridad);
System.out.println("\nTiempo de espera promedio: \n"+promedioEspera(PorPrioridad));
 System.out.println("\nTiempo total promedio: \n"+promedioTotal(PorPrioridad));




}

public static void planificacionPrioridadDerecho(){
     inicializar();
      ArrayList<Proceso> lista = Prioridad;
 ArrayList<Proceso> PorPrioridad = new ArrayList();
 ArrayList<Proceso> temporal =  new ArrayList();
 int i=0;
 int x;
 while(i<=140){
      for(int j=0;j<lista.size();j++){
          if(lista.get(j).getLlegada()==i){
             temporal.add(lista.get(j));
          }
      }
      
      //Verificar que la lista no es vacía
     if(!temporal.isEmpty()){
          x=mayorPrioridad(temporal);
         //Restar duración del proceso con mayor prioridad en ese instante
         temporal.get(x).setDuracion(temporal.get(x).getDuracion()-1);
         //En caso de que ya haya terminado el proceso, se elimina de temporal
         //
         
          
         for(int z=0;z<temporal.size();z++){
             
             
             if(z!=x){
    
                 temporal.get(z).settEspera(temporal.get(z).gettEspera()+1);
             }
             
         }
         
         
     }
     
     i++;
     
     if(!temporal.isEmpty()){
         x=mayorPrioridad(temporal);
         if(temporal.get(x).getDuracion()==0){
         PorPrioridad.add(temporal.get(x));
         temporal.remove(x);
         }
     }
     
     
 }
 
 for(int y=0;y<PorPrioridad.size();y++){
     for(int j=0;j<lista.size();j++){
        if(PorPrioridad.get(y).getProceso().equals(lista.get(j).getProceso())){
            PorPrioridad.get(y).setDuracion(listaProcesos.get(j).getDuracion());
             PorPrioridad.get(y).settTotal(PorPrioridad.get(y).getDuracion()+PorPrioridad.get(y).gettEspera());
         }
     }
     
 }
 Collections.sort(PorPrioridad, new Comparator<Proceso>() {
	
	public int compare(Proceso p1, Proceso p2) {
		return new String (p1.getProceso()).compareTo(new String(p2.getProceso()));
	}
});
 
 
 
 System.out.println("\nPLANIFICACIÓN POR PRIORIDAD CON DERECHO PREFERENTE\n");
 imprimir(PorPrioridad);
 
 System.out.println("\nTiempo de espera promedio: \n"+promedioEspera(PorPrioridad));
 System.out.println("\nTiempo total promedio: \n"+promedioTotal(PorPrioridad));
}

public static int mayorPrioridad(ArrayList<Proceso> lista){
    int mayor=0;
    int indice=0;
    int i;
    //Buscar la mayor prioridad 
    for( i=0;i<lista.size();i++){
        if(lista.get(i).getPrioridad()>mayor){
            mayor=lista.get(i).getPrioridad();
            indice=i;
        }
    }
    
    //Verificar si no hay dos procesos con la misma prioridad
    int rep=0;
      for( i=0;i<lista.size();i++){
      if(lista.get(i).getPrioridad()==mayor){
          rep++;
      }
  }
      //En caso de que existan más de un proceso con la misma prioridad
      //elegir el proceso que llegó primero
    if(rep!=1){
        int menor=10000;
      for( i=0;i<lista.size();i++){

          if(lista.get(i).getPrioridad()==mayor){

           if(lista.get(i).getLlegada()<menor){
               menor=lista.get(i).getLlegada();
               indice=i;
           }
          }
      }
     
    }  
    return indice;
}

public static void inicializar(){
    
  Prioridad.add(new Proceso("P1",14,8,5));
    Prioridad.add(new Proceso("P2",22,12,2));
    Prioridad.add(new Proceso("P3",8,0,8));
    Prioridad.add(new Proceso("P4",16,6,5));
    Prioridad.add(new Proceso("P5",26,24,7));
    Prioridad.add(new Proceso("P6",24,16,9));
    Prioridad.add(new Proceso("P7",12,20,4));
    Prioridad.add(new Proceso("P8",18,22,8));
}

public static float promedioEspera(ArrayList<Proceso> lista){
    float x=0;
    for(int i=0;i<lista.size();i++){
        x=x+lista.get(i).gettEspera();
    }
    x=x/lista.size();
    return x;
}
public static float promedioTotal(ArrayList<Proceso> lista){
    float x=0;
    for(int i=0;i<lista.size();i++){
        x=x+lista.get(i).gettTotal();
    }
    x=x/lista.size();
    return x;
}

}
