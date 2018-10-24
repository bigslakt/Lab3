package sample;

import javafx.scene.canvas.GraphicsContext;

//The interface Draw makes a promise that all classes implementing it will contain its method: draw
public interface Draw {

    //The method draw is used for drawing out shapes to the canvas
    void draw(GraphicsContext g);
}
