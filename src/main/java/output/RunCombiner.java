package output;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RunCombiner {

    public static void main(String[] args) throws IOException {
        String[] relativePaths = new String[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            relativePaths[i-1] = args[i];
        }
        combine(args[0], relativePaths);
    }

    // pretty dumb appending of files, doesn't attempt to strip comments or check parameters are the same
    public static void combine(String pathToSaveToString, String[] relativePaths) throws IOException {
        if (relativePaths.length == 0) {
            return;
        }

        Path pathToSaveTo = Paths.get(pathToSaveToString);

        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(Paths.get(relativePaths[0])), charset);
        for (int i = 1; i < relativePaths.length; i++) {
            String current = new String(Files.readAllBytes(Paths.get(relativePaths[i])), charset);
            content += current;
        }

        Files.write(pathToSaveTo, content.getBytes(charset));
    }
}
