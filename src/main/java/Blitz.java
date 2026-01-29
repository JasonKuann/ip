import java.io.IOException;
import java.util.Scanner;

public class Blitz {
    public static final String Line = "____________________________________________________________";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Blitz(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand(scanner);
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
                storage.save(tasks); // persist after successful command
            } catch (BlitzException e) {
                ui.showLine();
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showLine();
                System.out.println("Cannot save tasks: " + e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        new Blitz("./data/blitztasks.txt").run();
    }
}
