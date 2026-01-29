import java.io.IOException;

public class AddDeadlineCommand extends Command {
    private final String description;
    private final String by;

    public AddDeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, BlitzException {
        Task t = new Deadline(description, by);
        tasks.add(t);
        ui.showAdded(t, tasks.size());
    }
}
