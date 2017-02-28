package output;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PathCreator {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

    public static String createPathString(String path, String prefix, String filetype) {
        LocalDateTime now = LocalDateTime.now();
        return path + prefix + "~" + now.format(dateTimeFormatter) + filetype;
    }

    public static Path createPath(String path, String prefix, String filetype) {
        String pathString = createPathString(path, prefix, filetype);

        return Paths.get(pathString);
    }
}
