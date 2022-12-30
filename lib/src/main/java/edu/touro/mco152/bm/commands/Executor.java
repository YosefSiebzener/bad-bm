package edu.touro.mco152.bm.commands;

import edu.touro.mco152.bm.UiInterface;

public class Executor {

    public static void execute(CommandInterface command) {
        command.run();
    }
}
