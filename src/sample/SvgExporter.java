package sample;

import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SvgExporter implements FileExporter {

    @Override
    public void save(File file, ArrayList<Shape> shapesList, GraphicsContext g) {

        if (!file.exists()){

            Thread thread = new Thread(() ->{
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
