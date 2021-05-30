/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 * @author fer_c
 */
public class Proceso {

    private String proceso;
    private int duracion;
    private int llegada;
    private int prioridad;
    private int tEspera;
    private int tTotal;

    public Proceso(String proceso, int duracion, int llegada, int prioridad) {
        this.proceso = proceso;
        this.duracion = duracion;
        this.llegada = llegada;
        this.prioridad = prioridad;
        this.tEspera = 0;
        this.tTotal = 0;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getLlegada() {
        return llegada;
    }

    public void setLlegada(int llegada) {
        this.llegada = llegada;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int gettEspera() {
        return tEspera;
    }

    public void settEspera(int tEspera) {
        this.tEspera = tEspera;
    }

    public int gettTotal() {
        return tTotal;
    }

    public void settTotal(int tTotal) {
        this.tTotal = tTotal;
    }

    @Override
    public String toString() {
        return proceso + "\t\t" + duracion + "\t\t" + llegada + "\t\t" + prioridad + "\t\t" + tEspera + "\t\t" + tTotal;
    }
}
