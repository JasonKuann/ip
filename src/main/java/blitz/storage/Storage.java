package blitz.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import blitz.model.Deadline;
import blitz.model.Event;
import blitz.model.Task;
import blitz.model.Todo;

/**
 * Handles loading and saving tasks to the default file path.
 *
 * <p>Uses the same on-disk format as your original application:
 * TYPE | isDone(0/1) | description | extra...
 */
public final class Storage {
    /** Default storage file path (relative to working directory). */
    public static final String FILE_PATH = "./data/blitztasks.txt";

    private Storage() {
        // Utility class - prevent instantiation.
    }

    /**
     * Loads tasks from the default storage file.
     *
     * @return list of tasks loaded (never null)
     * @throws IOException if reading the file fails
     */
    public static List<Task> loadTasks() throws IOException {
        final List<Task> tasks = new ArrayList<>();
        final File file = new File(FILE_PATH);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] parts = line.split(" \\| ");
                Task task = null;
                switch (parts[0].trim()) {
                case "T":
                    task = new Todo(parts[2]);
                    break;
                case "D":
                    task = new Deadline(parts[2], parts[3]);
                    break;
                case "E":
                    task = new Event(parts[2], parts[3], parts[4]);
                    break;
                default:

                }
                if (task != null) {
                    if ("1".equals(parts[1].trim())) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
        }

        return tasks;
    }

    /**
     * Save tasks to the default storage file (overwrites existing file).
     *
     * @param tasks tasks to persist
     * @throws IOException if writing fails
     */
    public static void saveTasks(final List<Task> tasks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (final Task task : tasks) {
                String taskString = "";
                if (task instanceof Todo) {
                    taskString = "T | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription();
                } else if (task instanceof Deadline) {
                    taskString = "D | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription()
                            + " | " + ((Deadline) task).getBy();
                } else if (task instanceof Event) {
                    taskString = "E | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription()
                            + " | " + ((Event) task).getStartTime() + " | " + ((Event) task).getEndTime();
                }

                writer.write(taskString);
                writer.newLine();
            }
        }
    }
}
