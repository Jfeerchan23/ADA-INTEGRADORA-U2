/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import static Modelo.Prioridad.PRIORIDAD;
import java.util.ArrayList;
public class Main {

    /**
     * @param args the command line arguments
     */
     
    static ArrayList<Proceso> original = new ArrayList();
    public static void main(String[] args) {
        // TODO code application logic here
        inicializar();
        
        //Planificación por prioridad
        PRIORIDAD();
    
         
         
     
    }
private static void inicializar(){
    original.add(new Proceso("P1",14,8,5,0,0));
    original.add(new Proceso("P2",22,12,2,0,0));
    original.add(new Proceso("P3",8,0,8,0,0));
    original.add(new Proceso("P4",16,6,5,0,0));
    original.add(new Proceso("P5",26,24,7,0,0));
    original.add(new Proceso("P6",24,16,9,0,0));
    original.add(new Proceso("P7",12,20,4,0,0));
    original.add(new Proceso("P8",18,22,8,0,0));
}

  
  
  
  
}
