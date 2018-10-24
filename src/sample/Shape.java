package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

//The class Shape is an abstract super-class for creating shapes
//Shape implements Draw for drawing shapes to canvas and ShapeSelect for ability to click on shapes
public abstract class Shape implements Draw, ShapeSelect{

    Color color;
    double size;
    double x;
    double y;
    double height;
    double width;

    //Constructor of the class
    public Shape(Color color, double size, double x, double y)
    {
        this.color = color;
        this.size = size;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean isClicked(MouseEvent me) {

        return false;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void draw(GraphicsContext g) {

    }
}
