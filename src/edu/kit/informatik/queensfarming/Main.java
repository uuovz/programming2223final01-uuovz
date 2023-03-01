package edu.kit.informatik.queensfarming;

import edu.kit.informatik.queensfarming.ui.Shell;

/**
 * The application and entry point of the program.
 * The class is final and cannot be extended, and it also has a private constructor
 * to prevent instantiation of the utility class.
 *
 * @author uuovz
 * @version 1.0
 */
public final class Main {

    private static final String UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";
    private static final String ERROR_COMMAND_ARGUMENTS = "No Command Arguments Allowed.";

    /**
     Private constructor to prevent instantiation of the utility class.
     Throws an IllegalStateException with the error message UTILITY_CLASS_INSTANTIATION.
     */
    private Main() { throw new IllegalStateException(UTILITY_CLASS_INSTANTIATION); }

    /**
     The main method which starts the Shell for rover and user input.
     It checks if the args parameter is empty and starts the Shell.
     If args is not empty, it throws a GameException with the error message ERROR_COMMAND_ARGUMENTS.
     @param args an array of String arguments passed to the main method, which should be empty
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            Shell shell = new Shell();
            shell.start();
        } else {
            System.out.println(new GameException(ERROR_COMMAND_ARGUMENTS).getMessage());
        }

    }

}
