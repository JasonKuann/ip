import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

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

    public void save(TaskList tasks) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (Task task : tasks.asList()) {
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
