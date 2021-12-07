package cz.muni.fi.spnp.gui.components.diagram.graph.canvas;

/**
 * Functional interface for better readability than the default Runnable, which is associated with the threads.
 */
public interface VoidFunction {

    /**
     * Executes arbitrary code.
     */
    void call();

}
