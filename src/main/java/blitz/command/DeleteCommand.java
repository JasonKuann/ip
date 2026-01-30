package blitz.command;

import java.io.IOException;
import blitz.model.*;
import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.BlitzException;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BlitzException {
        if (index < 0 || index >= tasks.size()) throw new BlitzException("Invalid task number");
        Task removed = tasks.remove(index);
        ui.showRemoved(removed, tasks.size());
    }
}
