package output;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProbabilityDistributionFileWriter {

    private static final String ROOT_PATH = "data/";
    private static final String FILE_ENDING = ".data";

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

    // TODO: convert input here into tiny type?
    public void writeProbabilityDistributionMapToFile(Map<Integer, Double> probabilityDistribution,
                                                      String distributionName, String integerColumnName,
                                                      String doubleColumnName) throws IOException {
        Path path = Paths.get(createFilename(distributionName));
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {

            writer.write(integerColumnName + "  " + doubleColumnName + System.lineSeparator());

            List<String> linesToWrite =
                    probabilityDistribution
                    .keySet()
                    .stream()
                    .sorted()
                    .map(key -> "" + key + "    " + probabilityDistribution.get(key) + System.lineSeparator())
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
