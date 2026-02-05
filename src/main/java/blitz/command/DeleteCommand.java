package blitz.command;

import blitz.BlitzException;
import blitz.model.Task;
import blitz.model.TaskList;
import blitz.storage.Storage;
import blitz.ui.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BlitzException {
        if (index < 0 || index >= tasks.size()) {
            throw new BlitzException("Invalid task number");
        }
        Task removed = tasks.remove(index);
        ui.showRemoved(removed, tasks.size());
    }
}
