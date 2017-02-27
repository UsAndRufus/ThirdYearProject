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
        Path path = Paths.get(createFilename(probabilityDistribution.getName()));
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {

            writer.write("# Distribution name: " + probabilityDistribution.getName());
            writer.write("# Number of rows: " + kalahariParameters.getNumberOfRows() + ", number of columns: "
                    + kalahariParameters.getNumberOfColumns() + System.lineSeparator());
            writer.write("# Proportion vegetation: " + kalahariParameters.getProportionVegetation()
                    + System.lineSeparator());
            writer.write("# Fraction of cells to update every tick: "
                    + kalahariParameters.getFractionOfCellsToUpdateEveryTick()
                    + "; years: " + kalahariParameters.getYears() + System.lineSeparator());
            writer.write("# Immediacy factor: " + kalahariParameters.getDensityParameters().getImmediacyFactor()
                    + "; density distance: " + kalahariParameters.getDensityParameters().getMaximumDistance()
                    + System.lineSeparator());

            writer.write("# " + probabilityDistribution.getIntegerColumnName() + "    " +
                    probabilityDistribution.getDoubleColumnName() + System.lineSeparator());

            List<String> linesToWrite =
                    probabilityDistribution.getDistributionMap()
                    .keySet()
                    .stream()
                    .sorted()
                    .map(key -> "" + key + "    " + probabilityDistribution.getDistributionMap().get(key)
                            + System.lineSeparator())
                    .collect(Collectors.toList());

            // Writing in loop here rather than in stream due to checked exceptions
            for (String line : linesToWrite) {
                writer.write(line);
            }
        }
    }

    private String createFilename(String distributionName) {
        LocalDateTime now = LocalDateTime.now();
        return ROOT_PATH + distributionName + "~" + now.format(dateTimeFormatter) + FILE_ENDING;
    }

}
