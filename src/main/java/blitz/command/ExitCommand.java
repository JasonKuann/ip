package blitz.command;

import java.io.IOException;
import blitz.model.*;
import blitz.ui.Ui;
import blitz.storage.Storage;
import blitz.BlitzException;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        System.out.println("Bye. Hope to see you again soon!");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
