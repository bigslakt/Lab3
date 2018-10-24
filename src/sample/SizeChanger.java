package sample;


public class SizeChanger implements Command {

    private Shape shape;
    double size;
    double oldSize;

    public SizeChanger(Shape shape, double size) {

        this.shape = shape;
        this.size = size;
    }

    @Override
    public void execute() {

        oldSize = shape.getSize();
        this.shape.setSize(this.size);
    }

    @Override
    public void unExecute() {

        shape.setSize(oldSize);
    }
}
