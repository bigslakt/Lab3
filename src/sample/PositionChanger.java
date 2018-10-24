package sample;

//The class PositionChanger is the template for creating position-changers
//The class implements the interface Command (command pattern) to be able to use undo/redo commands
public class PositionChanger implements Command {

    private Shape shape;
    private double x;  //x and y is the new coordinates of the shape
    private double y;
    private double oldX;  //oldX and oldY is the coordinates of the shape before change
    private double oldY;

    //Constructor of the class
    //Takes a shape and the new coordinates of the shape
    public PositionChanger(Shape shape, double x, double y)
    {
        this.shape = shape;
        this.x = x;
        this.y = y;
    }

    //The method execute is used for executing a change of a shapes position (coordinates)
    //and are also used to redo after an undo is executed
    @Override
    public void execute() {

        oldX = shape.getX();  //The current state of a shapes x and y coordinates is saved in oldX and oldY
        oldY = shape.getY();
        this.shape.setX(this.x);  //A shape is getting new x and y coordinates
        this.shape.setY(this.y);
    }

    //The method unExecute is used to undo a change of a shapes position (coordinates)
    @Override
    public void unExecute() {

        shape.setX(oldX);  //A shape is getting its old coordinates
        shape.setY(oldY);

    }
}
