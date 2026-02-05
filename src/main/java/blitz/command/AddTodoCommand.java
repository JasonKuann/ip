package blitz.command;

import java.io.IOException;

import blitz.model.Task;
import blitz.model.TaskList;
import blitz.model.Todo;
import blitz.storage.Storage;
import blitz.ui.Ui;

public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        Task t = new Todo(description);
        tasks.add(t);
        ui.showAdded(t, tasks.size());
    }
}
