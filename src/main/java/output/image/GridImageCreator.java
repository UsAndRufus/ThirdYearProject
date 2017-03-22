package output.image;

import output.PathCreator;
import simulation.grid.Grid;
import simulation.grid.Position;
import simulation.grid.cell.Cell;
import simulation.grid.cell.CompetitorSpecies1;
import simulation.grid.cell.CompetitorSpecies2;
import simulation.grid.cell.Vegetation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GridImageCreator {

    private static final Color VEGETATION_COLOR = Color.BLACK;
    private static final Color SPECIES_1_COLOR = Color.BLUE;
    private static final Color SPECIES_2_COLOR = Color.RED;
    private static final Color NON_VEGETATION_COLOR = Color.white;

    private static final String PATH_TO_IMAGE = "data/images/";
    private static final String FILE_ENDING = ".png";
    private static final String IMAGE_TYPE = "PNG";

    public void createImage(Grid grid, String prefix) {
        BufferedImage image = new BufferedImage(grid.getNumberOfColumns(), grid.getNumberOfRows(),
                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < grid.getNumberOfColumns(); x++) {
            for (int y = 0; y < grid.getNumberOfColumns(); y++) {
                Cell cell = grid.getCell(new Position(x,y));
                if (cell instanceof Vegetation) {
                    image.setRGB(x, y, VEGETATION_COLOR.getRGB());
                } else if (cell instanceof CompetitorSpecies1) {
                    image.setRGB(x, y, SPECIES_1_COLOR.getRGB());
                } else if (cell instanceof CompetitorSpecies2) {
                    image.setRGB(x, y, SPECIES_2_COLOR.getRGB());
                } else {
                    image.setRGB(x,y, NON_VEGETATION_COLOR.getRGB());
                }
            }
        }

        saveImage(image, prefix);
    }

    private void saveImage(BufferedImage image, String prefix) {
        Path path = PathCreator.createPath(PATH_TO_IMAGE, prefix, FILE_ENDING);

        File file;
        try {
            file = new File(path.toString());
            ImageIO.write(image, IMAGE_TYPE, file);
            System.out.println("Wrote image of run to " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
