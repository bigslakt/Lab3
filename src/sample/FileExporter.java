package sample;

import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.util.ArrayList;

public interface FileExporter {

    void save(File file, ArrayList<Shape> shapesList, GraphicsContext g);
}
