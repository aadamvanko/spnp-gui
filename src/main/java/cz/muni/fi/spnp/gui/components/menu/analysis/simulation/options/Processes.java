package cz.muni.fi.spnp.gui.components.menu.analysis.simulation.options;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Processes {

    /**
     * Prevent construction.
     */
    private Processes() {
    }

    /**
     * @param command the command to run
     * @return the output of the command
     * @throws IOException if an I/O error occurs
     */
    public static String run(String... command) throws IOException {
        var processBuilder = new ProcessBuilder(command).redirectErrorStream(true);
        var process = processBuilder.start();
        var stringBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while (true) {
                String line = in.readLine();
                if (line == null)
                    break;
                stringBuilder.append(line).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

}
