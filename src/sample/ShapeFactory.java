package sample;

import javafx.scene.paint.Color;

//The class ShapeFactory is used for creating shapes of different types
public class ShapeFactory {

    //The method createShape takes a String telling which type to create and the values needed to create a shape
    //and then returns the shape
    public Shape createShape(String shapeType, Color color, double size, double x, double y){

        if(shapeType.equalsIgnoreCase("CIRCLE"))
        {
            return new Circle(color, size, x, y);
        }
        else if(shapeType.equalsIgnoreCase("SQUARE"))
        {
            return new Square(color, size, x, y);
        }
        else if(shapeType.equalsIgnoreCase("RECTANGLE"))
        {
            return new Rectangle(color, size, x, y);
        }
        else
        {
            return null;
        }
    }
}
