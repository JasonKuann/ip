package blitz;

import java.io.IOException;

import blitz.command.Command;
import blitz.model.TaskList;
import blitz.parser.Parser;
import blitz.storage.Storage;
import blitz.ui.GuiUi;

public class Blitz {
    private final TaskList tasks;
    private final GuiUi ui;
    private String commandType;

    public Blitz() {
        ui = new GuiUi();

        TaskList loaded;
        try {
            loaded = new TaskList(Storage.loadTasks());
        } catch (IOException e) {
            loaded = new TaskList();
        }
        tasks = loaded;
        assert tasks != null : "Task list should not be null!";
    }

    public String getResponse(String input) {
        ui.clear();
        try {
            Command c = Parser.parse(input);
            assert c != null : "Parsed command should not be null!";
            c.execute(tasks, ui);
            commandType = c.getClass().getSimpleName();

            assert tasks.asList() != null : "Tasks list should not be null when saving!";
            Storage.saveTasks(tasks.asList());
            return ui.getLastOutput().trim();

        } catch (BlitzException e) {
            commandType = "Error";
            ui.showError(e.getMessage());
            return ui.getLastOutput().trim();

        } catch (IOException e) {
            commandType = "Error";
            return "Cannot save tasks: " + e.getMessage();
        }
    }

    public String getCommandType() {
        return commandType;
    }
}
