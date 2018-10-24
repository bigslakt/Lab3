package sample;

import javafx.scene.paint.Color;



public class ShapeFactory {

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
