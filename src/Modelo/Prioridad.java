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
 * @author fer_c
 */
public class Prioridad {
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
public static void PRIORIDAD(){
  ArrayList<Proceso> lista = listaProcesos;
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


System.out.println("\nPLANIFICACIÓN POR PRIORIDAD\n");
  imprimir(PorPrioridad);





}

}
