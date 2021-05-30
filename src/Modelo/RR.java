package Modelo;

import java.util.List;

/**
 * @author Yao Chung Hu
 */
public class RR {

    public static void calculateRoundRobin(List<Proceso> processList, int quantum) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int[] burstTimeCopy = new int[processList.size()];
        int[] arrivalTimeCopy = new int[processList.size()];

        for (int i = 0; i < processList.size(); i++) {
            burstTimeCopy[i] = processList.get(i).getDuracion();
            arrivalTimeCopy[i] = processList.get(i).getLlegada();
        }

        int currentTime = 0;

        boolean flag = true;
        while (flag) {
            for (int i = 0; i < processList.size(); i++) {
                if (arrivalTimeCopy[i] <= currentTime) {
                    if (arrivalTimeCopy[i] > quantum) {
                        for (int j = 0; j < processList.size(); j++) {
                            if (arrivalTimeCopy[j] < arrivalTimeCopy[i]) {
                                if (burstTimeCopy[j] > 0) {
                                    flag = false;
                                    if (burstTimeCopy[j] > quantum) {
                                        currentTime += quantum;
                                        burstTimeCopy[j] = burstTimeCopy[j] - quantum;
                                        arrivalTimeCopy[j] = arrivalTimeCopy[j] + quantum;
                                    } else {
                                        currentTime += burstTimeCopy[j];
                                        processList.get(j).settTotal(currentTime - processList.get(j).getLlegada());
                                        processList.get(j).settEspera(currentTime - processList.get(j).getDuracion() - processList.get(j).getLlegada());
                                        burstTimeCopy[j] = 0;
                                    }
                                }
                            }
                        }
                    }
                    if (burstTimeCopy[i] > 0) {
                        flag = false;
                        if (burstTimeCopy[i] > quantum) {
                            currentTime += quantum;
                            burstTimeCopy[i] -= quantum;
                            arrivalTimeCopy[i] += quantum;
                        } else {
                            currentTime += burstTimeCopy[i];
                            processList.get(i).settTotal(currentTime - processList.get(i).getLlegada());
                            processList.get(i).settEspera(currentTime - processList.get(i).getDuracion() - processList.get(i).getLlegada());
                            burstTimeCopy[i] = 0;
                        }
                    }
                } else if (arrivalTimeCopy[i] > currentTime) {
                    currentTime++;
                    i--;
                }
            }
        }

        System.out.println("\nPID\tDuracion\tLlegada\tTiempo Espera\tTiempo Total");
        for (Proceso process : processList) {
            totalTurnaroundTime += process.gettTotal();
            totalWaitingTime += process.gettEspera();
            System.out.println(String.format("%s\t%s\t%s\t%s\t%s", process.getProceso(), process.getDuracion(), process.getLlegada(), process.gettEspera(), process.gettTotal()));
        }
        System.out.println("\nPromedio tiempo total: " + (float) totalTurnaroundTime / processList.size());
        System.out.println("Promedio tiempo de espera: " + (float) totalWaitingTime / processList.size());
    }
}
