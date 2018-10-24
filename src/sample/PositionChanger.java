package sample;


public class PositionChanger implements Command {

    private Shape shape;
    private double x;
    private double y;
    private double oldX;
    private double oldY;


    public PositionChanger(Shape shape, double x, double y)
    {
        this.shape = shape;
        this.x = x;
        this.y = y;

    }

    @Override
    public void execute() {

        oldX = shape.getX();
        oldY = shape.getY();
        this.shape.setX(this.x);
        this.shape.setY(this.y);
    }

    @Override
    public void unExecute() {

        shape.setX(oldX);
        shape.setY(oldY);

    }
}
