package sample;

//The class SizeChanger is the template for creating size-changers
//The class implements the interface Command (command pattern) to be able to use undo/redo commands
public class SizeChanger implements Command {

    private Shape shape;
    double size;  //size is the new size of the shape
    double oldSize;  //oldSize is the size of the shape before change

    //Constructor of the class
    //Takes a shape and the new size of the shape
    public SizeChanger(Shape shape, double size) {

        this.shape = shape;
        this.size = size;
    }

    //The method execute is used for executing a change of a shapes size
    //and are also used to redo after an undo is executed
    @Override
    public void execute() {

        oldSize = shape.getSize();  //The current state of a shapes size is saved in oldColor
        this.shape.setSize(this.size);  //A shape is getting a new size
    }

    //The method unExecute is used to undo a change of a shapes size
    @Override
    public void unExecute() {

        shape.setSize(oldSize);  //A shape is getting its old size
    }
}
