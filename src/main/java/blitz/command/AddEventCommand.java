package blitz.command;

import java.io.IOException;
import blitz.model.TaskList;
import blitz.model.Task;
import blitz.model.Event;
import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.BlitzException;

public class AddEventCommand extends Command {
    private final String description;
    private final String from;
    private final String to;

    public AddEventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws BlitzException, IOException {
        Task t = new Event(description, from, to);
        if (tasks.hasDuplicate(t)) {
            throw new BlitzException("This task is already in your list!");
        }
        tasks.add(t);
        ui.showAdded(t, tasks.size());
    }
}
