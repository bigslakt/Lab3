package sample;

import java.util.ArrayList;

//The class ShapeInserter is the template for creating shape-inserters that put shapes in to the list shapes
//The class implements the interface Command (command pattern) to be able to use undo/redo commands
public class ShapeInserter implements Command{

    ArrayList<Shape> shapes;
    private Shape shape;

    //Constructor of the class
    //Takes a ArrayList and a shape that is to be put in the list
    public ShapeInserter(ArrayList shapes, Shape shape){

        this.shapes = shapes;
        this.shape = shape;
    }

    //The method execute is used for putting a new shape in the list of shapes
    //and are also used to redo after an undo is executed
    @Override
    public void execute() {

        shapes.add(shape);  //A shape is added to the list
    }

    //The method unExecute is used to undo an insertion of a shape in to the list
    @Override
    public void unExecute() {

        shapes.remove(shape);  //A shape is removed from the list
    }
}
