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
    private static final String REPLACE_STRING = "REPLACE";
    private static final String FILE_TYPE = ".data";
    private static final Path FIT_SCRIPT_PATH = Paths.get("ThirdYearProject/gnuplot/fit.plt");
    private static final String CREATED_DIRECTORY_PATH_STRING = "ThirdYearProject/gnuplot/created/";

    public static void main(String[] args) throws IOException {
        System.out.println("Run from outside ThirdYearProject");
        if (!(args[0].startsWith("ThirdYearProject/"))) {
            System.out.println("Filepath argument must start with ThirdYearProject/");
            throw new IllegalArgumentException();
        }
        String dataFilePathString = args[0];

        createFor(Paths.get(dataFilePathString));

    }

    public static void createFor(Path dataFilePath) throws IOException {
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
        System.out.println(relativeFilePath);
        content = content.replaceAll(REPLACE_STRING, relativeFilePath);
        System.out.println(content);
        Files.write(fitScript, content.getBytes(charset));
    }
}
