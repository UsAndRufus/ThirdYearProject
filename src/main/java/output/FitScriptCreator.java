package output;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FitScriptCreator {
    private static final String REPLACE_PATH_STRING = "PATH";
    private static final String REPLACE_NAME_STRING = "NAME";

    private static final String FILE_TYPE = ".data";
    private static final Path FIT_SCRIPT_PATH = Paths.get("ThirdYearProject/gnuplot/fit.plt");
    private static final Path COMPETITORS_FIT_SCRIPT_PATH = Paths.get("ThirdYearProject/gnuplot/fit_competitors.plt");
    private static final String CREATED_DIRECTORY_PATH_STRING = "ThirdYearProject/gnuplot/created/";

    public static void main(String[] args) throws IOException {
        System.out.println("Run from outside ThirdYearProject");
        String dataFilePathString = args[0];

        System.out.println("args[0]: " + args[0] + "; args[1]: " + args[1]);

        if (args[1] != null) {
            createCompetitors(dataFilePathString, args[1]);
        } else {
            createFor(dataFilePathString);
        }
    }

    public static void createFor(String dataFilePathString) throws IOException {

        Path dataFilePath = createDataFilePath(dataFilePathString);

        Path createdFitScript = createFile(dataFilePath, FIT_SCRIPT_PATH);

        replaceFilename(createdFitScript, dataFilePath, "");
    }

    public static void createCompetitors(String species1PathString, String species2PathString) throws IOException {
        Path species1DataFilePath = createDataFilePath(species1PathString);
        Path species2DataFilePath = createDataFilePath(species2PathString);

        Path createdFitScript = createFile(species1DataFilePath, COMPETITORS_FIT_SCRIPT_PATH);

        replaceFilename(createdFitScript, species1DataFilePath, "_s1");
        replaceFilename(createdFitScript, species2DataFilePath, "_s2");
    }

    private static Path createDataFilePath(String dataFilePathString) {
        if (!(dataFilePathString.startsWith("ThirdYearProject/"))) {
            System.out.println("Filepath argument must start with ThirdYearProject/");
            throw new IllegalArgumentException();
        }

        return Paths.get(dataFilePathString);
    }

    private static Path createFile(Path dataFilePath, Path fitScriptPath) throws IOException {
        String filename = dataFilePath.getFileName().toString();
        filename = "fit_" + filename.substring(0, filename.length() - FILE_TYPE.length());
        Path createdFitScript = Paths.get(CREATED_DIRECTORY_PATH_STRING + filename);
        Files.copy(fitScriptPath, createdFitScript, REPLACE_EXISTING, COPY_ATTRIBUTES);

        return createdFitScript;
    }

    private static void replaceFilename(Path fitScript, Path dataFilePath, String postfix) throws IOException {
        Charset charset = StandardCharsets.UTF_8;

        String content = new String(Files.readAllBytes(fitScript), charset);
        String relativeFilePath = fitScript.getParent().relativize(dataFilePath).toString();
        relativeFilePath = relativeFilePath.replace("\\", "/");
        content = content.replaceAll(REPLACE_PATH_STRING + postfix, relativeFilePath);
        content = content.replaceAll(REPLACE_NAME_STRING + postfix, dataFilePath.getFileName().toString());
        Files.write(fitScript, content.getBytes(charset));
    }
}
