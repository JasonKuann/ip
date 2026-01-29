public class UnmarkCommand extends Command {
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws BlitzException {
        if (index < 0 || index >= tasks.size()) throw new BlitzException("Invalid task number");
        Task t = tasks.get(index);
        t.markNotDone();
        ui.showUnmarked(t);
    }
}
