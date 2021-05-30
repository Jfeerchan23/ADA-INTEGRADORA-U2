package Modelo;

import java.util.List;

/**
 * @author Yao Chung Hu
 */
public class RR {

    public static void calculateRoundRobin(List<Proceso> processList, int quantum) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int[] remainingBurstTime = new int[processList.size()];

        int currentTime = 0;
        for (int i = 0; i < processList.size(); i++) {
            remainingBurstTime[i] = processList.get(i).getDuracion();
        }

        while (true) {
            boolean done = true;
            for (int i = 0; i < processList.size(); i++) {
                if (remainingBurstTime[i] > 0) {
                    done = false;
                    if (remainingBurstTime[i] > quantum) {
                        currentTime += quantum;
                        remainingBurstTime[i] -= quantum;
                    } else {
                        currentTime += remainingBurstTime[i];
                        processList.get(i).settEspera(currentTime - processList.get(i).getDuracion());
                        remainingBurstTime[i] = 0;
                    }
                }
            }
            if (done)
                break;
        }

        System.out.println("\nPID\tDuracion\tLlegada\tTiempo Espera\tTiempo Total");
        for (Proceso process : processList) {
            process.settTotal(process.gettEspera() + process.getDuracion());
            totalWaitingTime += process.gettEspera();
            totalTurnaroundTime += process.gettTotal();
            System.out.println(String.format("%s\t%s\t%s\t%s\t%s", process.getProceso(), process.getDuracion(), process.getLlegada(), process.gettEspera(), process.gettTotal()));
        }
        System.out.println("\nPromedio tiempo total: " + (float) totalTurnaroundTime / processList.size());
        System.out.println("Promedio tiempo de espera: " + (float) totalWaitingTime / processList.size());
    }
}
