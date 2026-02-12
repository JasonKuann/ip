package blitz.command;

import java.io.IOException;
import blitz.model.TaskList;
import blitz.model.Task;
import blitz.model.Todo;
import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.BlitzException;

public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws IOException {
        Task t = new Todo(description);
        tasks.add(t);
        ui.showAdded(t, tasks.size());
    }
}
