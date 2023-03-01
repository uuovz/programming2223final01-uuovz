package edu.kit.informatik.queensfarming.ui;

/**
 * An enum representing the different execution states that the application can go through.
 *
 * @author uuovz
 * @version 1.0
 */

public enum ExecutionState {
    /**
     * The program is currently running.
     */
    RUNNING,
    /**
     * The program is finished and result will be printed.
     */
    FINISHED,
    /**
     * The program is finished and result was printed. Programm will shut down shortly.
     */
    EXITED,
}
