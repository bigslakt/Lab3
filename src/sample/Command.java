package sample;

//The interface Command makes a promise that all classes implementing it will contain its methods: execute and unexecute
//Command is used for implementing the command pattern for undo and redo
public interface Command {

    //The method execute is used for executing a change of a shape and for adding a new shape to the list
    //execute is also used for redo an operation that was undone by the unExecute method
    void execute();

    //The method unExecute is used to undo an operation made by the execute method
    void unExecute();
}
