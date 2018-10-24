package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Shape implements Draw, ShapeSelect{

    Color color;
    double size;
    double x;
    double y;
    double height;
    double width;

    public Shape(Color color, double size, double x, double y)
    {
        this.color = color;
        this.size = size;
        this.x = x;
        this.y = y;
    }

    public Shape(Shape shape) {
        this(shape.getColor(), shape.getSize(), shape.getX(), shape.getY());
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public void draw(GraphicsContext g) {

    }
}
