package sample;

import javafx.scene.paint.Color;


public class ColorChanger implements Command {

    private Shape shape;
    private Color color;
    private Color oldColor;


    public ColorChanger(Shape shape, Color color) {

        this.shape = shape;
        this.color = color;
    }

    @Override
    public void execute() {

        oldColor = shape.getColor();
        this.shape.setColor(this.color);
    }

    @Override
    public void unExecute() {

        shape.setColor(oldColor);
    }
}
