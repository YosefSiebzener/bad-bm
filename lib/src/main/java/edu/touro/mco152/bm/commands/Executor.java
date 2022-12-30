package edu.touro.mco152.bm.commands;

public class Executor {

    /**
     * Method for executing commands
     * @param command The command to be executed.
     */
    public static void execute(CommandInterface command) {
        command.run();
    }
}
