package sample;

import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//The class SvgExporter is a template to create objects that is used for saving shapes to SVG format
//SvgExporter implements FileExporter and uses its method save
public class SvgExporter implements FileExporter {

    //The method save is used to save shapes from the canvas to file
    @Override
    public void save(File file, ArrayList<Shape> shapesList, GraphicsContext g) {

        if (!file.exists()){

            Thread thread = new Thread(() ->{  //Lambda expression for writing to file in new thread
                try {

                    FileWriter writer = new FileWriter(file);
                    writer.write("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" canvasWidth=\"800\" canvasHeight=\"800\">\n");
                    writer.write("<rect canvasWidth=\"600\" canvasHeight=\"400\" fill=\"WHITE\"/>\n");

                    for (Shape shape : shapesList) {
                        writer.write(shape + "\n");
                    }

                    writer.write("</svg>");
                    writer.close();
                }

                catch (IOException e){
                }
            });

            thread.start();
        }
    }
}
