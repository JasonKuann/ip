package blitz.command;

import java.io.IOException;
import blitz.model.TaskList;
import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.BlitzException;

import java.io.IOException;

public abstract class Command {
    protected String output = "";

    public abstract void execute(TaskList tasks, Ui ui) throws BlitzException, IOException;

    public String getString() {
        return output;
    }

    public boolean isExit() { 
        return false; 
    }
}
