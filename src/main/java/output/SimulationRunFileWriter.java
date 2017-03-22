package output;

import simulation.SimulationParameters;
import simulation.density.DensityParameters;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimulationRunFileWriter {

    private static final String ROOT_PATH = "data/";
    private static final String FILE_ENDING = ".data";

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

    public void writeSimulationRunToFile(String name, ProbabilityDistribution probabilityDistribution,
                                         SimulationParameters simulationParameters, double proportionVegetation,
                                         DensityParameters densityParameters) throws IOException {
        Path path = PathCreator.createPath(ROOT_PATH, name, FILE_ENDING);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {

            writeHeader(writer, probabilityDistribution, simulationParameters, proportionVegetation, densityParameters);

            writer.write("# " + probabilityDistribution.getIntegerColumnName() + "    " +
                    probabilityDistribution.getDoubleColumnName() + System.lineSeparator());

            List<String> linesToWrite =
                    probabilityDistribution.getDistributionMap()
                    .keySet()
                    .stream()
                    .sorted()
                    .map(key -> "" + 16*key + "    " + probabilityDistribution.getDistributionMap().get(key)
                            + System.lineSeparator())
                    .collect(Collectors.toList());

            // Writing in loop here rather than in stream due to checked exceptions
            for (String line : linesToWrite) {
                writer.write(line);
            }
        }
    }

    private void writeHeader(Writer writer, ProbabilityDistribution probabilityDistribution,
                             SimulationParameters simulationParameters, double proportionVegetation,
                             DensityParameters densityParameters) throws IOException {
        writer.write("# distribution: " + probabilityDistribution.getName() + System.lineSeparator());
        writer.write("# rows: " + simulationParameters.getNumberOfRows() + System.lineSeparator());
        writer.write("# columns: " + simulationParameters.getNumberOfColumns() + System.lineSeparator());
        writer.write("# targetProportion: " + proportionVegetation + System.lineSeparator());
        writer.write("# fractionOfCellsToUpdateEveryTick: "
                + simulationParameters.getFractionOfCellsToUpdateEveryTick() + System.lineSeparator());
        writer.write("# years: " + simulationParameters.getYears() + System.lineSeparator());
        writer.write("# immediacyFactor: " + densityParameters.getImmediacyFactor() +System.lineSeparator());
        writer.write("# maximumDistance: " + densityParameters.getMaximumDistance() +System.lineSeparator());
    }

}
