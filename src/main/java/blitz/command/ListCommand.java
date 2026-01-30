package blitz.command;

import java.io.IOException;
import blitz.model.*;
import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.BlitzException;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}
