import java.util.Scanner;

public class Blitz {
    public static final String Line = "____________________________________________________________";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(Line);
        System.out.println(" Hello! I'm Blitz");
        System.out.println(" What can I do for you?");
        System.out.println(Line);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                System.out.println(Line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(Line);
                break;
            } else if (input.equals("nothing")) {
                System.out.println(Line);
                System.out.println("Then i can finally enjoy some break!");
                System.out.println(Line);
            } else {
                System.out.println(Line);
                System.out.println(" " + input);
                System.out.println(Line);
            }
        }
        scanner.close();
    }
}
