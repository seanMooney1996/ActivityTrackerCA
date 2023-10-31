import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private String[] options;
    private String menuHeader;

    public Menu(String[] options, String menuHeader) {
        this.options = options;
        this.menuHeader = menuHeader;
    }

    public void display() {

    // Display the top row with '+' symbols
        System.out.println("+ ---------------------------------------------- +");

        // Display the menu header
        System.out.printf("|\t\t\t  %-34s |\n", this.menuHeader);

        System.out.println("|\t\t\t                                     |");

        // Display the options
        for (int i = 0; i < options.length; i++) {
            System.out.printf("|\t\t\t  %-2d. %-30s |\n", (i + 1), options[i]);
        }

        // Display the exit option
        System.out.println("|\t\t\t                                     |");
        System.out.println("|\t\t\t  0. Exit                            |");

        // Display the bottom row with '+' symbols
        System.out.println("+ ---------------------------------------------- +");

    }

    public int getChoice() {
        Scanner kb = new Scanner(System.in);

        // using true not as a variable to stay in this loop forever until the return statement is reached
        while(true)
        {
            if (kb.hasNextInt()) {
                int choice = kb.nextInt();

                if (choice >= 0 && choice <= options.length) {
                    return choice;
                }
            }
            System.out.println("Invalid input. Please enter a number between 0 and " + options.length);
            kb.next(); // Consume invalid input
        }
    }

    public static void displaySelectActivity() {
        System.out.println("+ ------ Select Activity ----- +");
        System.out.println("|                               |");
        System.out.println("|         1. Running            |");
        System.out.println("|         2. Swimming           |");
        System.out.println("|         3. Cycling            |");
        System.out.println("|                               |");
        System.out.println("+ ----------------------------- +");
    }

    public static boolean selectOrder(){
        Scanner kb = new Scanner(System.in);
        System.out.println("+ ------- Select Order ------- +");
        System.out.println("|                              |");
        System.out.println("|         1. Ascending         |");
        System.out.println("|         2. Descending        |");
        System.out.println("|                              |");
        System.out.println("+ ---------------------------- +");

        while (!kb.hasNextInt()) {
            System.out.println("Select 1 for ASCENDING | 2 for DESCENDING");
            kb.next(); // Consume invalid input
        }
        int choice = kb.nextInt();
        if(choice == 1)
            return  true; // Ascending
        else
            return false; // Descending
    }

}
