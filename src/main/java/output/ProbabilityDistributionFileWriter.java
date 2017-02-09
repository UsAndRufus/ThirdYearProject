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

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    public void writeProbabilityDistributionMapToFile(Map<Integer, Double> probabilityDistribution,
                                                      String distributionName) throws IOException {
        Path path = Paths.get(createFilename(distributionName));
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            List<String> linesToWrite =
                    probabilityDistribution
                    .keySet()
                    .stream()
                    .sorted()
                    .map(key -> "" + key + "    " + probabilityDistribution.get(key) + "\n")
                    .collect(Collectors.toList());

            for (String line : linesToWrite) {
                writer.write(line);
            }
        }
    }

    private String createFilename(String distributionName) {
        LocalDateTime now = LocalDateTime.now();
        return distributionName + "~" + now.format(dateTimeFormatter);
    }

}
