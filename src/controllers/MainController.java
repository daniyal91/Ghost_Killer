package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import misc.Utils;
import model.Game;
import model.GameGrid;
import model.Path;
import views.MainView;

/**
 * Main controller for the game program. Will listen to actions from the user in the MainView, and choose what new view
 * to instantiate accordingly. This class implements the singleton pattern.
 *
 * @author SnapDragon
 *
 */
public class MainController implements Runnable, ActionListener {

    /**
     * The only instance of this Singleton class.
     */
    private static MainController instance = new MainController();

    private MainView mainFrame;

    /**
     * Returns the single instance of the MainController class.
     *
     * @return the single instance of the MainController class.
     * @roseuid 5837CF4002BC
     */
    public static MainController getInstance() {
        return MainController.instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            this.mainFrame = new MainView(this);
            this.mainFrame.setVisible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     *
     * Can execute the following action: Launch the game with a selected map.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == this.mainFrame.buttonNewGame) {
            String filePath = Utils.selectFile();
            if (filePath != null) {

                Game game = new Game();
                game.grid.readFromFile(filePath, true);
                GameController gameController = new GameController(game);

                Path t = new Path(game.grid);

            }

        } 

    }
}
