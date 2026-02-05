package blitz.command;

import java.io.IOException;

import blitz.BlitzException;
import blitz.model.TaskList;
import blitz.storage.Storage;
import blitz.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws BlitzException, IOException;
    public boolean isExit() {
        return false;
    }
}
