package blitz.ui;

import blitz.model.Task;
import blitz.model.TaskList;
import java.util.ArrayList;

public class GuiUi extends Ui {
    private static final String LINE = "____________________________________________________________";
    private String lastOutput = "";

    public String getLastOutput() {
        return lastOutput;
    }

    public void clear() {
        lastOutput = "";
    }

    @Override
    public void showLine() {
        lastOutput += LINE + "\n";
    }

    @Override
    public void showError(String message) {
        lastOutput += "Error: " + message + "\n";
    }

    @Override
    public void showList(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        if (tasks.size() == 0) {
            sb.append("Currently no task ongoing");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
            }
        }
        lastOutput += sb.toString().trim() + "\n";
    }

    @Override
    public void showAdded(Task task, int size) {
        lastOutput += "Got it. I've added this task:\n"
                + "  " + task + "\n"
                + "Now you have " + size + " tasks in the list.\n";
    }

    @Override
    public void showRemoved(Task task, int size) {
        lastOutput += "Noted. I've removed this task:\n"
                + "  " + task + "\n"
                + "Now you have " + size + " tasks in the list.\n";
    }

    @Override
    public void showMarked(Task task) {
        lastOutput += "Nice! I've marked this task as done:\n"
                + "  " + task + "\n";
    }

    @Override
    public void showUnmarked(Task task) {
        lastOutput += "OK, I've marked this task as not done yet:\n"
                + "  " + task + "\n";
    }

    @Override
    public void showFound(ArrayList<Task> matches) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");

        if (matches.isEmpty()) {
            sb.append("No matching tasks found.");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                sb.append(i + 1).append(".").append(matches.get(i)).append("\n");
            }
        }

        lastOutput += sb.toString().trim() + "\n";
    }
}
