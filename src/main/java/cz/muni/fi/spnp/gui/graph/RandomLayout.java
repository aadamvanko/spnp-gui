package cz.muni.fi.spnp.gui.graph;

import java.util.List;
import java.util.Random;

public class RandomLayout extends Layout {

    GraphView graphView;

    Random rnd = new Random();

    public RandomLayout(GraphView graphView) {

        this.graphView = graphView;

    }

    public void execute() {

        List<Cell> cells = graphView.getModel().getAllCells();

        for (Cell cell : cells) {

            double x = rnd.nextDouble() * 500;
            double y = rnd.nextDouble() * 500;

            cell.relocate(x, y);

        }

    }

}
