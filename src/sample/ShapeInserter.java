package sample;

import java.util.ArrayList;


public class ShapeInserter implements Command{

    ArrayList<Shape> shapes;
    private Shape shape;

    public ShapeInserter(ArrayList shapes, Shape shape){

        this.shapes = shapes;
        this.shape = shape;
    }

    @Override
    public void execute() {

        shapes.add(shape);
    }

    @Override
    public void unExecute() {

        shapes.remove(shape);
    }
}
