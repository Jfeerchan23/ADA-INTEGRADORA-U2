package Modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yao Chung Hu
 */
public class SJF {

    public static void calculateShortestJobFirst(List<Proceso> processList) {
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        Map<String, Boolean> processState = new HashMap<>();
        for (Proceso proceso : processList) {
            processState.put(proceso.getProceso(), false);
        }

        int currentTime = 0;
        int completed = 0;

        while(completed != processList.size()) {
            Proceso proceso = null;
            int min = 10000000;
            for (Proceso process: processList) {
                if (process.getLlegada() <= currentTime && !processState.get(process.getProceso())) {
                    if (process.getDuracion() < min) {
                        min = process.getDuracion();
                        proceso = process;
                    }
                    if(process.getDuracion() == min) {
                        if(process.getLlegada() < proceso.getLlegada()) {
                            min = process.getDuracion();
                            proceso = process;
                        }
                    }
                }
            }
            if(proceso != null) {
                proceso.settTotal(currentTime + proceso.getDuracion() - proceso.getLlegada());
                proceso.settEspera(proceso.gettTotal() - proceso.getDuracion());

                totalTurnaroundTime += proceso.gettTotal();
                totalWaitingTime += proceso.gettEspera();

                processState.put(proceso.getProceso(), true);
                completed++;
                currentTime = currentTime + proceso.getDuracion();
            }
            else {
                currentTime++;
            }
        }


        System.out.println("\nPID\tDuracion\tLlegada\tTiempo Espera\tTiempo Total");
        for (Proceso proceso: processList) {
            System.out.println(String.format("%s\t%s\t%s\t%s\t%s", proceso.getProceso(), proceso.getDuracion(), proceso.getLlegada(), proceso.gettEspera(), proceso.gettTotal()));
        }
        System.out.println("\nPromedio tiempo total: " + (float) totalTurnaroundTime / processList.size());
        System.out.println("Promedio tiempo de espera: " + (float) totalWaitingTime / processList.size());
    }
}
