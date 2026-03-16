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
        this.ui = new GuiUi();
        this.tasks = loadTasks();
    }

    private TaskList loadTasks() {
        try {
            TaskList loaded = new TaskList(Storage.loadTasks());
            assert loaded != null : "Task list should not be null!";
            return loaded;
        } catch (IOException e) {
            TaskList emptyList = new TaskList();
            assert emptyList != null : "Fallback task list should not be null!";
            return emptyList;
        }
    }

    public String getResponse(String input) {
        ui.clear();

        try {
            Command command = parseCommand(input);
            executeCommand(command);
            saveTasks();
            return getTrimmedOutput();
        } catch (BlitzException e) {
            return handleBlitzException(e);
        } catch (IOException e) {
            return handleIOException(e);
        }
    }

    private Command parseCommand(String input) throws BlitzException {
        Command command = Parser.parse(input);
        assert command != null : "Parsed command should not be null!";
        return command;
    }

    private void executeCommand(Command command) throws BlitzException {
        command.execute(tasks, ui);
        commandType = command.getClass().getSimpleName();
    }

    private void saveTasks() throws IOException {
        assert tasks.asList() != null : "Tasks list should not be null when saving!";
        Storage.saveTasks(tasks.asList());
    }

    private String handleBlitzException(BlitzException e) {
        commandType = "Error";
        ui.showError(e.getMessage());
        return getTrimmedOutput();
    }

    private String handleIOException(IOException e) {
        commandType = "Error";
        return "Cannot save tasks: " + e.getMessage();
    }

    private String getTrimmedOutput() {
        return ui.getLastOutput().trim();
    }

    public String getCommandType() {
        return commandType;
    }
}
