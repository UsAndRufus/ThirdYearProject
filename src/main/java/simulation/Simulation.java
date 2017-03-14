package simulation;

import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Simulation {
    protected Grid grid;
    protected int years;
    protected double fractionOfCellsToUpdateEveryTick;

    public Simulation(SimulationParameters simulationParameters) {
        this.fractionOfCellsToUpdateEveryTick = simulationParameters.getFractionOfCellsToUpdateEveryTick();
        this.years = simulationParameters.getYears();
    }

    public void run(boolean print) {

        if (print) {
            System.out.println("Starting simulation");
        }

        if (print) {
            for (int year = 0; year < years; year ++) {
                System.out.print("X");
            }
            System.out.println();
        }

        for (int year = 0; year < years; year++) {
            tick();
            if (print) {
                System.out.print("X");
            }

        }
        if (print) {
            System.out.println();

            System.out.println("done");
        }

    }

    protected void tick() {
        List<Position> positions = grid.getRandomPositions(fractionOfCellsToUpdateEveryTick);

        Map<Position, Cell> transitions = new HashMap<>(positions.size());

        for (Position position : positions) {
            transitions.put(position, positionShouldTransitionTo(position));
        }

        for (Position position : transitions.keySet()) {
            grid.setCell(position, transitions.get(position));
        }
    }

    abstract protected Cell positionShouldTransitionTo(Position position);
}
