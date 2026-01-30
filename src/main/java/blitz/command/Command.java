package blitz.command;

import java.io.IOException;
import blitz.model.*;
import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.BlitzException;

import java.io.IOException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BlitzException, IOException;
    public boolean isExit() { 
        return false; 
    }
}