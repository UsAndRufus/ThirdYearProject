package output;

import simulation.KalahariParameters;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationRunFileWriter {

    private static final String ROOT_PATH = "data/";
    private static final String FILE_ENDING = ".data";

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

    public void writeSimulationRunToFile(ProbabilityDistribution probabilityDistribution,
                                         KalahariParameters kalahariParameters) throws IOException {
        Path path = PathCreator.createPath(ROOT_PATH, probabilityDistribution.getName(), FILE_ENDING);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {

            writer.write("#" + probabilityDistribution.getName() + System.lineSeparator());
            writer.write("#" + kalahariParameters.getNumberOfRows() + System.lineSeparator());
            writer.write("#" + kalahariParameters.getNumberOfColumns() + System.lineSeparator());
            writer.write("#" + kalahariParameters.getProportionVegetation() + System.lineSeparator());
            writer.write("#" + kalahariParameters.getFractionOfCellsToUpdateEveryTick() + System.lineSeparator());
            writer.write("#" + kalahariParameters.getYears() + System.lineSeparator());
            writer.write("# " + kalahariParameters.getDensityParameters().getImmediacyFactor() +System.lineSeparator());
            writer.write("# " + kalahariParameters.getDensityParameters().getMaximumDistance() +System.lineSeparator());

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

}
