package blitz.storage;

import java.io.*;
import java.util.ArrayList;

import blitz.model.*;

/**
 * Storage helper responsible for loading and saving tasks to disk.
 *
 * <p>Uses the text format: TYPE | isDone(0/1) | description | extra...
 */
public final class Storage {
    /** Default file path for storing tasks. */
    public static final String FILE_PATH = "./data/blitztasks.txt";

    private Storage() {
        // utility class - prevent instantiation
    }

    /**
     * Loads tasks from the default file path.
     *
     * @return an ArrayList of Task objects loaded from disk
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static ArrayList<Task> loadTasks() throws IOException {
        final ArrayList<Task> tasks = new ArrayList<>();
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
                        // ignore unknown record type
                }

                if (task != null) {
                    if (parts[1].trim().equals("1")) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
        }

        return tasks;
    }

    /**
     * Save the provided tasks to the default storage file (overwrites file).
     *
     * @param tasks the tasks to persist
     * @throws IOException if writing fails
     */
    public static void saveTasks(final ArrayList<Task> tasks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                String taskString = "";
                if (task instanceof Todo) {
                    taskString = "T | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription();
                } else if (task instanceof Deadline) {
                    taskString = "D | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription() + " | " + ((Deadline) task).getBy();
                } else if (task instanceof Event) {
                    taskString = "E | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription() + " | " + ((Event) task).getStartTime() + " | " + ((Event) task).getEndTime();
                }

                writer.write(taskString);
                writer.newLine();
            }
        }
    }
}
