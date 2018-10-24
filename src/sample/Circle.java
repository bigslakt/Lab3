package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

//The class Circle is a template to create circles
public class Circle extends Shape{

    //Constructor of the class
    public Circle(Color color, double size, double x, double y){super (color, size, x, y);

        this.height = size * 0.5;  //The size of the cirkle is devided equally to height and width
        this.width = size * 0.5;
        this.x = x;
        this.y = y;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {

        this.height = size * 0.5;
        this.width = size * 0.5;
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

    //The method draw is used to draw objects to the canvas
    @Override
    public void draw(GraphicsContext g) {

        g.setFill(color);
        g.fillOval(x - width * 0.5, y - height * 0.5, width, height);
    }

    //The method isClicked is used to check if an object is clicked (selected) by the user on the canvas
    @Override
    public boolean isClicked(MouseEvent me)
    {
        if(Math.sqrt(Math.pow(x - me.getX(), 2) + Math.pow(y - me.getY(), 2)) < width * 0.5){
            return true;
        }
        return false;
    }

    //toString is used by the SvgExporter for saving to SVG format
    @Override
    public String toString() {

        String tmpColor = String.format("#%02X%02X%02X",
                (int) (getColor().getRed()*255),
                (int) (getColor().getGreen()*255),
                (int) (getColor().getBlue()*255));

        String string = "<circle cx=\"" + this.x +"\" cy=\"" + this.y + "\" " +
                "r=\"" + this.width/2 + "\" fill=\"" + tmpColor + "\"/>";

        return string;
    }
}
