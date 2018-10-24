package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PngExporter implements  FileExporter{

    Canvas canvas = new Canvas(600,400);

    @Override
    public void save(File file, ArrayList<Shape> shapesList, GraphicsContext g) {

        g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        g.fillRect(0,0,600,400);
        for (Shape shape : shapesList) {
            shape.draw(g);
        }
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
        Thread thread = new Thread( () -> {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            }
            catch (IOException e) {
            }
        });
        thread.start();
    }
}
