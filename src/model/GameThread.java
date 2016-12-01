package model;

/**
 * The main game thread. This thread will call the makeTurn action of the Game class until it is interrupted.
 *
 * @author SnapDragon
 *
 */
public class GameThread extends Thread {

    /**
     * Default delay of activation of the tread.
     */
    public static int DEFAULT_DELAY = 1000;

    private Game game;

    /**
     * We don't use the interrupted mechanism of the Thread class because of it's side effects. We want the current
     * thread to finish it's current executing instead of brutally interrupting it.
     */
    private boolean isStopped = false;

    /**
     * Creates a new GameThread associated with a game.
     *
     * @param game Game to associate with the GameThread.
     *  @roseuid 5837CC25005B
     */
    public GameThread(Game game) {
        this.game = game;
    }

    /**
     * Will call the makeTurn function on the associated Game object.
     *  @roseuid 5837CC25887I
     */
    @Override
    public synchronized void run() {

        if (this.isStopped) {
            return;
        }

        try {
            this.wait(GameThread.DEFAULT_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.game.makeTurn();
        this.run();
    }

    /**
     * Stops the current thread from executing another game turn. The current turn will not be interrupted.
     *  @roseuid 5837CC250663
     */
    public void stopThread() {
        this.isStopped = true;
    }

}
