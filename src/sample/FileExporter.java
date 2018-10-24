package sample;

import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.util.ArrayList;

//The interface FileExporter makes a promise that all classes implementing it will contain its method: save
public interface FileExporter {

    //The method save is used to save shapes from the canvas to file
    void save(File file, ArrayList<Shape> shapesList, GraphicsContext g);
}
