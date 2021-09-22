package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimulationTask extends Task<Void> {

    private final StringBuilder stringBuilder;
    private final String[] command;
    private Process process;
    private boolean isProcessDestroyed;

    public SimulationTask(String... command) {
        this.stringBuilder = new StringBuilder();
        this.command = command;
    }

    @Override
    protected Void call() {
        var processBuilder = new ProcessBuilder(command).redirectErrorStream(true);
        try {
            process = processBuilder.start();
        } catch (Exception e) {
            System.err.println("Could not run simulation/analysis due to the error:");
            e.printStackTrace();
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while (true) {
                if (isCancelled()) {
                    destroyProcess();
                    return null;
                }
                String line = in.readLine();
                if (line == null)
                    break;
                stringBuilder.append(line);
                if (!line.isEmpty()) {
                    stringBuilder.append(System.lineSeparator());
                }
            }
        } catch (Exception e) {
            System.err.println("Simulation/Analysis has failed due to the error:");
            e.printStackTrace();
        }
        destroyProcess();
        return null;
    }

    private void destroyProcess() {
        if (isProcessDestroyed) {
            return;
        }

        var processBuilder = new ProcessBuilder("cmd.exe", "/c", "taskkill /f /t /pid " + process.pid());
        try {
            Process processTaskkill = processBuilder.start();
            isProcessDestroyed = true;
        } catch (Exception e) {
            System.err.println("Error during killing of the old simulation/analysis:");
            e.printStackTrace();
        }
    }

    @Override
    protected void succeeded() {
        super.succeeded();

        System.out.println(stringBuilder);
        System.out.println("Simulation/Analysis has finished.");
        destroyProcess();
    }

    @Override
    protected void cancelled() {
        super.cancelled();

        System.out.println("Simulation/Analysis has been cancelled.");
        destroyProcess();
    }

    @Override
    protected void failed() {
        super.failed();

        System.err.println("Simulation/Analysis has failed.");
        destroyProcess();
    }

}
