package blitz.command;

import java.io.IOException;
import blitz.model.TaskList;
import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.BlitzException;

public class MarkCommand extends Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BlitzException {
        if (index < 0 || index >= tasks.size()) throw new BlitzException("Invalid task number");
        Task t = tasks.get(index);
        t.markAsDone();
        ui.showMarked(t);
    }
}
