package blitz.command;

import java.io.IOException;
import java.util.ArrayList;

import blitz.BlitzException;
import blitz.model.Task;
import blitz.model.TaskList;
import blitz.ui.Ui;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws BlitzException, IOException {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new BlitzException("Find what? Please provide a keyword.");
        }

        String k = keyword.trim().toLowerCase();
        ArrayList<Task> matches = new ArrayList<>();

        for (Task t : tasks.asList()) {
            if (t.getDescription().toLowerCase().contains(k)) {
                matches.add(t);
            }
        }

        ui.showFound(matches);
    }
}
