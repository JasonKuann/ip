package blitz.command;

import java.io.IOException;
import blitz.model.TaskList;
import blitz.model.Task;
import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.BlitzException;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showList(tasks);
    }
}
