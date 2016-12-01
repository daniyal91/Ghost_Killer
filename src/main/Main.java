package main;

import java.awt.EventQueue;

import controllers.MainController;

/**
 * This class is the main class containing the entry point of the program.
 * @author SnapDragon
 *
 */
public class Main {

    /**
     * Entry point of the program.
     *
     * @param args command line arguments.
     * @roseuid 5837CF4003CA
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(MainController.getInstance());
    }

}
