package blitz.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import blitz.model.Task;
import blitz.model.Todo;
import blitz.model.Deadline;
import blitz.model.Event;
import blitz.model.TaskList;

public class Storage {

    public static final String FILE_PATH = "./data/blitztasks.txt";

    public static ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
            return tasks;
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" \\| ");
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
            }

            if (task != null) {
                if (parts[1].trim().equals("1")) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
        }

        reader.close();
        return tasks;
    }

    public static void saveTasks(ArrayList<Task> tasks) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));

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

        writer.close();
    }
}
