package blitz.command;

import blitz.model.TaskList;
import blitz.storage.Storage;
import blitz.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}
