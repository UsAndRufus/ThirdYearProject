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
    private static final String CREATED_DIRECTORY_PATH_STRING = "ThirdYearProject/gnuplot/created/";

    public static void main(String[] args) throws IOException {
        System.out.println("Run from outside ThirdYearProject");
        String dataFilePathString = args[0];

        createFor(dataFilePathString);

    }

    public static void createFor(String dataFilePathString) throws IOException {
        if (!(dataFilePathString.startsWith("ThirdYearProject/"))) {
            System.out.println("Filepath argument must start with ThirdYearProject/");
            throw new IllegalArgumentException();
        }

        Path dataFilePath = Paths.get(dataFilePathString);

        String filename = dataFilePath.getFileName().toString();
        filename = "fit_" + filename.substring(0, filename.length() - FILE_TYPE.length());
        Path createdFitScript = Paths.get(CREATED_DIRECTORY_PATH_STRING + filename);
        Files.copy(FIT_SCRIPT_PATH, createdFitScript, REPLACE_EXISTING, COPY_ATTRIBUTES);

        replaceFilename(createdFitScript, dataFilePath);
    }

    private static void replaceFilename(Path fitScript, Path dataFilePath) throws IOException {
        Charset charset = StandardCharsets.UTF_8;

        String content = new String(Files.readAllBytes(fitScript), charset);
        String relativeFilePath = fitScript.getParent().relativize(dataFilePath).toString();
        relativeFilePath = relativeFilePath.replace("\\", "/");
        content = content.replaceAll(REPLACE_PATH_STRING, relativeFilePath);
        content = content.replaceAll(REPLACE_NAME_STRING, dataFilePath.getFileName().toString());
        Files.write(fitScript, content.getBytes(charset));
    }
}