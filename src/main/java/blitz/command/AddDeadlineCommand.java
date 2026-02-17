package blitz.command;

import java.io.IOException;
import blitz.model.TaskList;
import blitz.model.Task;
import blitz.model.Deadline;
import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.BlitzException;

public class AddDeadlineCommand extends Command {
    private final String description;
    private final String by;

    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws IOException, BlitzException {
        Task t = new Deadline(description, by);
        if (tasks.hasDuplicate(t)) {
            throw new BlitzException("This task is already in your list!");
        }
        tasks.add(t);
        ui.showAdded(t, tasks.size());
    }
}
