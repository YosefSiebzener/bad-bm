package edu.touro.mco152.bm.commands;

/**
 * Commands must implement this interface to be able to be executed
 * by the executor
 */
public interface CommandInterface {

    /**
     * Method used by executor to run the command
     */
    public void run();
}
