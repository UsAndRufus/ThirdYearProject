package output;

import javafx.util.Pair;
import simulation.SimulationParameters;
import simulation.density.DensityParameters;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class SimulationRunFileWriter {

    private static final String ROOT_PATH = "data/";
    private static final String FILE_ENDING = ".data";
    private static final String WHITESPACE = "    ";

    public void writeSimulationRunToFile(String name, ProbabilityDistribution probabilityDistribution,
                                         SimulationParameters simulationParameters, double proportionVegetation,
                                         DensityParameters densityParameters) throws IOException {
        Path path = PathCreator.createPath(ROOT_PATH, name, FILE_ENDING);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {

            writeHeader(writer, probabilityDistribution.getName(), simulationParameters, proportionVegetation,
                    densityParameters);

            writer.write("# " + probabilityDistribution.getIntegerColumnName() + WHITESPACE +
                    probabilityDistribution.getDoubleColumnName() + System.lineSeparator());

            List<String> linesToWrite =
                    probabilityDistribution.getDistributionMap()
                    .keySet()
                    .stream()
                    .sorted()
                    .map(key -> "" + 16*key + WHITESPACE + probabilityDistribution.getDistributionMap().get(key)
                            + System.lineSeparator())
                    .collect(Collectors.toList());

            // Writing in loop here rather than in stream due to checked exceptions
            for (String line : linesToWrite) {
                writer.write(line);
            }
        }
    }

    public void writeMultiRunToFile(String name, Map<Double,Double> weightingAgainstRatios) throws IOException {
        Path path = PathCreator.createPath(ROOT_PATH, name, FILE_ENDING);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("# " + "Weighting" + WHITESPACE + "Ratio" + System.lineSeparator());


            List<String> linesToWrite =
                    weightingAgainstRatios
                            .keySet()
                            .stream()
                            .sorted()
                            .map(key -> "" + key + WHITESPACE + weightingAgainstRatios.get(key) + System.lineSeparator())
                            .collect(Collectors.toList());

            for (String line : linesToWrite) {
                writer.write(line);
            }
        }
    }

    public void writeCompetitorsRunToFile(String name, ProbabilityDistribution species1ProbabilityDistribution,
                                          ProbabilityDistribution species2ProbabilityDistribution,
                                          SimulationParameters simulationParameters, double proportionVegetation,
                                          DensityParameters densityParameters) throws IOException{
        Path path = PathCreator.createPath(ROOT_PATH, name, FILE_ENDING);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writeHeader(writer, species1ProbabilityDistribution.getName(), simulationParameters, proportionVegetation,
                    densityParameters);

            writer.write("# " + species1ProbabilityDistribution.getIntegerColumnName() + "    " +
                    species1ProbabilityDistribution.getDoubleColumnName() + "    " +
                    species2ProbabilityDistribution.getDoubleColumnName() + System.lineSeparator());

            Map<String, Pair<String, String>> combined
                    = createCombinedMap(species1ProbabilityDistribution.getDistributionMap(),
                    species2ProbabilityDistribution.getDistributionMap());

            for (String key : combined.keySet()) {
                String line = key + WHITESPACE + combined.get(key).getKey() + WHITESPACE + combined.get(key).getValue()
                        + System.lineSeparator();
                writer.write(line);
            }

        }
    }

    // doesn't work
    private Map<String, Pair<String, String>> createCombinedMap(Map<Integer, Double> species1Map,
                                                                 Map<Integer, Double> species2Map) {
        Map<String, Pair<String, String>> combined = new HashMap<>();

        Iterator<Integer> species1Iterator = species1Map.keySet().iterator();
        Iterator<Integer> species2Iterator = species2Map.keySet().iterator();

        int s1Key = 0;
        int s2Key = 0;

        while (species1Iterator.hasNext() || species2Iterator.hasNext()) {
            if ((s1Key < s2Key) && (s1Key != 0)) {
                s1Key = (species1Iterator.hasNext()) ? species1Iterator.next() : 0;
            } else if ((s1Key > s2Key) && (s2Key != 0)) {
                s2Key = (species2Iterator.hasNext()) ? species2Iterator.next() : 0;
            } else {
                s1Key = (species1Iterator.hasNext()) ? species1Iterator.next() : 0;
                s2Key = (species2Iterator.hasNext()) ? species2Iterator.next() : 0;
            }

            if (s1Key == s2Key) {
                combined.put(Integer.toString(s1Key), new Pair<>(Double.toString(species1Map.get(s1Key)),
                        Double.toString((species2Map.get(s2Key)))));
            } else {
                if ((s1Key != 0) && !species2Map.keySet().contains(s1Key)) {
                    //System.out.println("s1Key = " + s1Key + ", species1Map.get(s1Key) = " + species1Map.get(s1Key));
                    combined.put(Integer.toString(s1Key), new Pair<>(Double.toString(species1Map.get(s1Key)), "X"));
                }
                if ((s2Key != 0) && !species1Map.keySet().contains(s2Key)) {
                    combined.put(Integer.toString(s2Key), new Pair<>("X", Double.toString(species2Map.get(s2Key))));
                }
            }
        }

        return combined;
    }

    private void writeHeader(Writer writer, String distributionName, SimulationParameters simulationParameters,
                             double proportionVegetation, DensityParameters densityParameters) throws IOException {
        writer.write("# distribution: " + distributionName + System.lineSeparator());
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
