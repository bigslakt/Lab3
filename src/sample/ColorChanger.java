package sample;

import javafx.scene.paint.Color;

//The class ColorChanger is the template for creating color-changers
//The class implements the interface Command (command pattern) to be able to use undo/redo commands
public class ColorChanger implements Command {

    private Shape shape;
    private Color color;  //color is the new color of the shape
    private Color oldColor;  //oldColor is the color of the shape before change

    //Constructor of the class
    //Takes a shape and the new color of the shape
    public ColorChanger(Shape shape, Color color) {

        this.shape = shape;
        this.color = color;
    }

    //The method execute is used for executing a change of a shapes color
    //and are also used to redo after an undo is executed
    @Override
    public void execute() {

        oldColor = shape.getColor();  //The current state of a shapes color is saved in oldColor
        this.shape.setColor(this.color);  //A shape is getting a new color
    }

    //The method unExecute is used to undo a change of a shapes color
    @Override
    public void unExecute() {

        shape.setColor(oldColor);  //A shape is getting its old color
    }
}
