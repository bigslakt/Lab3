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

    private ArrayList<Shape> shapes = new ArrayList<>();  //The list thats storing shapes
    private ShapeFactory shapeFactory = new ShapeFactory();  //ShapeFactory is used to decide which shape type to create
    private Command command;  //Command is used to implement undo/redo (command pattern)

    private int undoRedoPointer = -1;  //Keeps track on the elements in the CommandStack
    private Stack<Command> commandStack = new Stack<>();  //commandStack stores executed commands

    @FXML
    private Canvas canvas;  //The Canvas that shapes are drawn onto
    @FXML
    private ColorPicker colorPicker;  //The colorPicker is the color menu
    @FXML
    private TextField textField;  //The textField is where the user write the wanted size on a shape

    private double canvasWidth;  //The canvas´s width and height
    private double canvasHeight;
    private ToggleButton shapeButton;  //shapeButton is the toggleButtons that is used for deciding shape to create
    private Shape shape;  //Created shape ready for placing on canvas
    private Shape selectedShape;  //selectedShape is selected with the right mouse key
    private String shapeType;  //shapeType stores an String of the shape type the user have chosen to create
    private Color color;  //color is the picked color
    private double size;  //size is the size that the user have decided
    private GraphicsContext g;  //The graphic for the canvas

    //The method initialize starts when the program starts
    public void initialize()
    {
        canvasWidth = canvas.getWidth();  //The canvas gets its width and height
        canvasHeight = canvas.getHeight();

        g = canvas.getGraphicsContext2D();  //The graphic for the canvas

        g.clearRect(0, 0, canvasWidth, canvasHeight);  //Settings for the canvas
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, 600, 400);

        //Listener for the textField for changing size
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {

                if(selectedShape != null && !newValue.equals(""))  //If a shape is selected and the textfield is not empty
                {
                    changeSize(newValue);  //The method changeSize is called
                }
            }
        });
    }

    //ActionEvent for clicking shapeButtons
    public void shapeClick(ActionEvent actionEvent) {

        selectedShape = null;
        shapeButton = (ToggleButton) actionEvent.getSource();
        shapeType = shapeButton.getId();
    }

    //MouseEvent for clicking on canvas
    public void canvasClick(MouseEvent mouseEvent) {

        double x = mouseEvent.getX();  //Getting the coordinates from the mouse click
        double y = mouseEvent.getY();

        if(mouseEvent.getButton() == MouseButton.PRIMARY)  //If the left mouse button is clicked
        {
            if(selectedShape != null)  //If shape is selected the shape gets a new position
            {
                deleteElementsAfterPointer(undoRedoPointer);  //The method deleteElementsAfterPointer is called

                command = new PositionChanger(selectedShape, x, y);  //Object of PositionChanger is created and stored as Command
                command.execute();  //The method execute is called for the positionChanger
                commandStack.push(command);  //The command is put (pushed) in the commandStack
                redrawCanvas();  //The method redrawCanvas is called
                undoRedoPointer++;  //undoRedoPointer is added by one because of the new added command

                selectedShape = null;  //selected shape is unselected
            }

            else if (shapeType != null)  //Else if shape type is chosen
            {
                color = colorPicker.getValue();  //Gets color from colorPicker
                size = Double.parseDouble(textField.getText());  //Gets size from textField

                shape = shapeFactory.createShape(shapeType, color, size, x, y);  //Shape is created

                if(shape != null)  //If shape was successfully created
                {
                    deleteElementsAfterPointer(undoRedoPointer);  //The method deleteElementsAfterPointer is called

                    command = new ShapeInserter(shapes, shape);  //Object of ShapeInserter is created and stored as Command
                    command.execute();  //The method execute is called for the ShapeInserter
                    commandStack.push(command);  //The command is put (pushed) in the commandStack
                    redrawCanvas();  //The method redrawCanvas is called
                    undoRedoPointer++;  //undoRedoPointer is added by one because of the new added command
                }

                shapeType = null;  //shapeType becomes null so the user cant make a new shape without choosing type
                shapeButton.setSelected(false);  //The toggleButton shapeButton becomes unselected
            }
        }
        else  //Else (right mouse button is clicked)
        {
            for (Shape s : shapes)  //Loop that loops through the shapes list to see if one is clicked
            {
                if(s.isClicked(mouseEvent))  //If shape is clicked
                {
                    selectedShape = s;  //selectedShape is a clicked shape
                }
            }
        }
    }

    //The method redrawCanvas redraws the canvas
    private void redrawCanvas()
    {
        g.clearRect(0, 0, canvasWidth, canvasHeight);
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, 600, 400);

        for (Shape shape : shapes) {  //Loop for drawing all the shapes from the list shapes to the canvas
            shape.draw(g);
        }
    }

    //The method changeSize is used for changing size on a shape
    private void changeSize(String newSize)
    {
        size = Double.parseDouble(newSize);  //Gets the size from the textField

        deleteElementsAfterPointer(undoRedoPointer);  //The method deleteElementsAfterPointer is called

        command = new SizeChanger(selectedShape, size); //Object of SizeChanger is created and stored as Command
        command.execute();  //The method execute is called for the SizeChanger
        commandStack.push(command);  //The command is put (pushed) in the commandStack
        redrawCanvas();  //The method redrawCanvas is called
        undoRedoPointer++;  //undoRedoPointer is added by one because of the new added command
    }

    //The method changeColor is used for changing color on a shape
    public void changeColor() {

        if(selectedShape != null)  //If shape is selected
        {
            color = colorPicker.getValue();  //Gets the color from the colorPicker

            deleteElementsAfterPointer(undoRedoPointer);  //The method deleteElementsAfterPointer is called

            command = new ColorChanger(selectedShape, color);  //Object of ColorChanger is created and stored as Command
            command.execute();  //The method execute is called for the ColorChanger
            commandStack.push(command);  //The command is put (pushed) in the commandStack
            redrawCanvas();  //The method redrawCanvas is called
            undoRedoPointer++;  //undoRedoPointer is added by one because of the new added command

            selectedShape = null;
        }
    }

    //The method deleteElementsAfterPointer is used for deleting commands done after the undoRedoPointers value (element)
    //in the commandStack when a new command is to be done
    private void deleteElementsAfterPointer(int undoRedoPointer)
    {
        if(commandStack.size()<1)
            return;
        for(int i = commandStack.size()-1; i > undoRedoPointer; i--)
        {
            commandStack.remove(i);
        }
    }

    //The method undo is used for undo executions that is done
    public void undo() {

        if(undoRedoPointer >= 0)  //If at least one command has been made
        {
            Command command = commandStack.get(undoRedoPointer);  //Gets the current command
            command.unExecute();  //The method unExecute is called for the current command

            undoRedoPointer--;  //undoRedoPointer is subtracted by one because of one command is being undone

            redrawCanvas();  //The method redrawCanvas is called
        }
    }

    //The method redo restores an previous undone command
    public void redo() {

        if(undoRedoPointer == commandStack.size() - 1)  //If no command has been made
            return;
        undoRedoPointer++;  //undoRedoPointer is added by one because of the new added command
        Command command = commandStack.get(undoRedoPointer);  //Gets the current command
        command.execute();  //The method execute is called for the current command
        redrawCanvas();  //The method redrawCanvas is called
    }

    //The method saveToFile is called from the GUI window when clicking save (under file)
    //The method opens a window for choosing file name and format
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

        if(file != null)  //If file creation succeeded
        {
            writeToFile(file, shapes);  //The method writeToFile is called
        }
        else  //Else (file creation did'nt succeed)
        {
            redrawCanvas();  //The method redrawCanvas is called in case of an output text may already exist
            canvas.getGraphicsContext2D().fillText("File not saved...", 100.0,100.0);  //Prints text in canvas
        }
    }

    //The method writeToFile are dooing the actual saving
    private void writeToFile(File file, ArrayList<Shape> shapes)
    {
        String fileName = file.getName();  //Gets the file-name
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());

        FileExporter fileExporter = FileExporterFactory.createSaveFormat(fileExtension);  //Creates the fileExporter

        redrawCanvas(); //The method redrawCanvas is called in case of an output text may already exist

        try
        {
            fileExporter.save(file, shapes, g);  //The method save in the fileExporter is called for the saving
            canvas.getGraphicsContext2D().fillText("File saved...", 100.0,100.0);  //Prints text in canvas
        }
        catch(Exception e)
        {
            canvas.getGraphicsContext2D().fillText("File not saved...", 100.0,100.0);  //Prints text in canvas
        }
    }
}
