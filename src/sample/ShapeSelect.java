package sample;

import javafx.scene.input.MouseEvent;

//The interface ShapeSelect makes a promise that all classes implementing it will contain its method: isClicked
public interface ShapeSelect {

    //The method isClicked is used to check if an object is clicked (selected) by the user on the canvas
    boolean isClicked(MouseEvent me);
}
