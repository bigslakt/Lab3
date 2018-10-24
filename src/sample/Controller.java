package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

public class Controller {

    ArrayList<Shape> shapes = new ArrayList<>();  //listan som lagrar shapes
    ShapeFactory shapeFactory = new ShapeFactory();  //Shape Factory används för att avgöra vilken shape som ska byggas
    Command command;  //Command används för att spara undo/redo kommandon i commandstack

    private int undoRedoPointer = -1;  //Håller reda på hur många kommandon som lagrats i stacken och deras positioner
    private Stack<Command> commandStack = new Stack<>();  //Stacken lagrar kommandon

    @FXML
    private Canvas canvas;  //Canvasen som shapse målas på
    @FXML
    private ColorPicker colorPicker;  //Colorpickern är färg-vals menyn
    @FXML
    private TextField textField;  //Textfältet där storlek för en shape skrivs in

    double canvasWidth;
    double canvasHeight;
    private ToggleButton shapeButton;  //Variabel för toggle buttons
    private Shape shape;  //Färdig shape klar för placering på canvasen
    private Shape selectedShape;  //Selected shape efter högerklick
    private String shapeType;  //Shapetype lagrar vald shape efter användaren valt en shape i fönstret
    private Color color;  //Vald färg
    private double size;  //Inställd storlek
    private double x;  //Position i x led
    private double y;  //Position i Y led
    private GraphicsContext g;  //grafiken för canvasen

    //Denna metoden körs vid start och ligger i bakgrunden
    public void initialize()
    {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        g = canvas.getGraphicsContext2D();

        g.clearRect(0, 0, canvasWidth, canvasHeight);
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, 600, 400);

        //Listener för textfältet när man ändrar storlek
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                if(selectedShape != null && !newValue.equals(""))
                {
                    changeSize(newValue);
                }
            }
        });
    }

    //Event för när man valt en shape i fönstret
    public void shapeClick(ActionEvent actionEvent) {

        selectedShape = null;
        shapeButton = (ToggleButton) actionEvent.getSource();
        shapeType = shapeButton.getId();
    }

    //Event med villkorssats för klick på canvasen
    public void canvasClick(MouseEvent mouseEvent) {

        x = mouseEvent.getX();
        y = mouseEvent.getY();

        if(mouseEvent.getButton() == MouseButton.PRIMARY)  //Om vänsterklick
        {
            if(selectedShape != null)  //Om shape blivit selectedd med höger klick och ska flyttas
            {
                deleteElementsAfterPointer(undoRedoPointer);

                command = new PositionChanger(selectedShape, x, y);
                command.execute();
                commandStack.push(command);
                redrawCanvas();
                undoRedoPointer++;

                selectedShape = null;
            }

            else if (shapeType != null)  //Om användaren valt en shape i fönstret
            {
                color = colorPicker.getValue();
                size = Double.parseDouble(textField.getText());

                shape = shapeFactory.createShape(shapeType, color, size, x, y);

                if(shape != null)  //Om shape skapats och finns
                {
                    deleteElementsAfterPointer(undoRedoPointer);

                    command = new ShapeInserter(shapes, shape);
                    command.execute();
                    redrawCanvas();

                    commandStack.push(command);
                    undoRedoPointer++;
                }

                shapeType = null;
                shapeButton.setSelected(false);
            }
        }
        else  //Annars om högerklick
        {
            for (Shape s : shapes)  //Listan över shapes loopas för att se om en shape blivit klickad på
            {
                if(s.isClicked(mouseEvent))
                {
                    selectedShape = s;  //Om shape blivit vald så lagras den i selectedShape
                }
            }
        }
    }

    //Metoden ritar om canvasen
    private void redrawCanvas()
    {
        g.clearRect(0, 0, canvasWidth, canvasHeight);
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, 600, 400);

        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }

    //Metoden används för att byta storlek på shape
    public void changeSize(String newSize)
    {
        size = Double.parseDouble(newSize);

        deleteElementsAfterPointer(undoRedoPointer);

        command = new SizeChanger(selectedShape, size);

        command.execute();
        commandStack.push(command);
        redrawCanvas();
        undoRedoPointer++;
    }

    //Metoden används för att byta färg på shape
    public void changeColor() {

        if(selectedShape != null)
        {
            color = colorPicker.getValue();

            deleteElementsAfterPointer(undoRedoPointer);

            command = new ColorChanger(selectedShape, color);
            command.execute();

            commandStack.push(command);
            redrawCanvas();
            undoRedoPointer++;

            selectedShape = null;
        }
    }

    //Metoden tar bort element efter nytt kommando utförts efter man gjort en undo i commandStacken
    private void deleteElementsAfterPointer(int undoRedoPointer)
    {
        if(commandStack.size()<1)
            return;
        for(int i = commandStack.size()-1; i > undoRedoPointer; i--)
        {
            commandStack.remove(i);
        }
    }

    //Metoden används när man ångrar något man gjort
    public void undo() {

        if(undoRedoPointer >= 0)
        {
            Command command = commandStack.get(undoRedoPointer);
            command.unExecute();

            undoRedoPointer--;

            redrawCanvas();
        }
    }

    //Redo återskapar något man tagit bort
    public void redo() {

        if(undoRedoPointer == commandStack.size() - 1)
            return;
        undoRedoPointer++;
        Command command = commandStack.get(undoRedoPointer);
        command.execute();
        redrawCanvas();
    }

    //saveToFile anropas när man ska spara från fönstret och anropar i sin tur metoden writeToFile
    public void saveToFile() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Open resource File");

        String path = System.getProperty("user.home") + File.separator;
        File initialDir = new File(path);
        fileChooser.setInitialDirectory(initialDir);

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PNG-image (*.png) ","*.png");
        fileChooser.getExtensionFilters().add(filter);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG-image (*.svg) ","*.svg"));

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        if(file != null)
        {
            writeToFile(file, shapes);
        }
        else  //Om sparning blir avbruten så skrivs det ut
        {
            redrawCanvas();  //Ritar om canvasen ifall annan utskrift skulle finnas kvar
            canvas.getGraphicsContext2D().fillText("File not saved...", 100.0,100.0);
        }
    }

    //writeToFile utför sparandet till fil
    private void writeToFile(File file, ArrayList<Shape> shapes)
    {
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());

        FileExporter fileExporter = FileExporterFactory.createSaveFormat(fileExtension);

        redrawCanvas(); //Ritar om canvasen ifall annan utskrift skulle finnas kvar

        try
        {

            fileExporter.save(file, shapes, g);
            canvas.getGraphicsContext2D().fillText("File saved...", 100.0,100.0);
        }
        catch(Exception e)
        {
            canvas.getGraphicsContext2D().fillText("File not saved...", 100.0,100.0);
        }
    }
}
