package model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import model.GameGrid.CASE_TYPES;
import model.tower.ExplosionTower;
import model.tower.FireTower;
import model.tower.IceTower;
import model.tower.Tower;
import model.tower.TowerFactory;

/**
 * This class implements the main gaming logic in which user can buy, sell, upgrade towers. It is also observable so
 * that a view class can be notified of internal changes.
 *
 * @author SnapDragon
 *
 */
public class Game extends Observable {

    /**
     * Initial amount of money that the player has to buy towers.
     */
    public static final int INITIAL_MONEY = 100;

    /**
     * Initial amount of lives the player has.
     */
    public static final int INITIAL_LIVES = 3;

    /**
     * Number of ghosts released per wave.
     */
    private static final int GHOSTS_PER_WAVE = 3;

    /**
     * Number of waves the player has to go through before winning the game.
     */
    private static final int WAVES_TO_WIN = 3;

    /**
     * List of available towers that the user can buy.
     */
    public static Tower[] AVAILABLE_TOWERS = {new FireTower(), new IceTower(), new ExplosionTower()};
    public int deadCount = INITIAL_LIVES;

    public GameGrid grid;
    public HashMap<Point, Ghost> ghosts = new HashMap<Point, Ghost>();
    public ArrayList<GridLocation> attackedGhosts;
    public Path shortestPath;
    public boolean startlog = true;

    private HashMap<Point, Tower> towers = new HashMap<Point, Tower>();
    private int money;
    private GameThread gameThread;
    private int ghostsReleased;
    private int lives;
    private int wave;
    private int killedGhosts = 0;

    /**
     * Constructs the Game object with an empty 100x100 grid.
     * @roseuid 5837CF4002FA
     */
    public Game() {
        this.grid = new GameGrid(100, 100);
        this.money = Game.INITIAL_MONEY;
        this.lives = Game.INITIAL_LIVES;
        this.shortestPath = new Path(this.grid);
        this.wave = 1;
        this.attackedGhosts = new ArrayList<GridLocation>();
    }

    /**
     * Gets the current amount of money the player has.
     *
     * @return an integer representing the amount of money the player has
     * @roseuid 5837CF401243
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * Sets the current amount of the money the player has.
     *
     * @param money an integer representing the amount of money the player has.
     * @roseuid 5837CF40CFAA
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Buys a news tower and place it at the specified position on the grid.
     *
     * @param tower The tower to buy.
     * @param line Line where to place the new tower.
     * @param column Column where to place the new tower.
     * @roseuid 5837CB4400CD
     */
    public void buyTower(Tower tower, int line, int column) {
        if (tower.getInitialCost() > this.money) {
            return;
        } else if (this.hasTower(line, column)) {
            return;
        }
        this.money -= tower.getInitialCost();
        Tower newTower = TowerFactory.createTower(tower.getName());
        newTower.setLocation(new GridLocation(line, column));
        this.addTower(newTower, line, column);
    }

    /**
     * Sells a tower at a specific location of the game grid.
     *
     * @param line Line where to place the new tower.
     * @param column Column where to place the new tower.
     * @roseuid 5837CB4400AD
     */
    public void sellTower(int line, int column) {
        Tower tower = this.getTower(line, column);
        this.money += tower.refundAmout();
        this.towers.remove(new Point(line, column));
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Adds a tower at the specific location.
     *
     * @param t Type of tower to place.
     * @param line Line where to place the new tower.
     * @param column Column where to place the new tower.
     * @roseuid 5837BB4400AA
     */
    public void addTower(Tower t, int line, int column) {
        Point location = new Point(line, column);
        this.towers.put(location, t);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Checks if there is a tower at a specific location.
     *
     * @param line Line where to check for tower.
     * @param column Column where to check for tower.
     *
     * @return True if there is a tower at that location.
     * @roseuid 5837CB4100AA
     */
    public boolean hasTower(int line, int column) {
        Point location = new Point(line, column);
        return this.towers.get(location) != null;
    }

    /**
     * Returns the tower at a specific location.
     *
     * @param line Line where tower is.
     * @param column Column where the tower is.
     *
     * @return Tower located at the specified location
     * @roseuid 5837CB440000
     */
    public Tower getTower(int line, int column) {
        Point location = new Point(line, column);
        return this.towers.get(location);
    }

    /**
     * Gets the towers.
     *
     * @return Hash map of the tower on the grid, indexed by their location.
     * @roseuid 5837CB1400BD
     */
    public HashMap<Point, Tower> getTowers() {
        return towers;
    }

    /**
     * Sets the towers.
     *
     * @param towers Hash map of the tower to place on the grid, indexed by their location.
     * @roseuid 5837CB440097
     */
    public void setTowers(HashMap<Point, Tower> towers) {
        this.towers = towers;
    }

    /**
     * Upgrade the level of the tower at a specific location.
     *
     * @param line Line of the tower to upgrade.
     * @param column Column of the tower to upgrade.
     * @roseuid 5837BC4412CD
     */
    public void upgradeTower(int line, int column) {
        Tower tower = this.getTower(line, column);
        if (this.money >= tower.getLevelCost()) {
            tower.upgradeLevel();
            this.money -= tower.getLevelCost();
            this.setChanged();
            this.notifyObservers();
        }
    }

    /**
     * Initiates a new wave of ghosts.
     * @roseuid 5837CA9A0348
     */
    public void sendWave() {
        if (this.gameThread != null) {
            return;
        }
        this.ghostsReleased = 0;
        this.gameThread = new GameThread(this);
        gameThread.start();
    }

    /**
     * Add a ghosts on the game grid.
     *
     * @param ghost The ghost to place on the grid.
     * @roseuid 5837CA9A0399
     */
    public void addGhost(Ghost ghost) {
        this.ghosts.put(ghost.gridLocation, ghost);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Determines if there is a ghost at the specified location on the grid.
     *
     * @param location Location to verify if there is a ghost
     *
     * @return A boolean indicating if there is a ghost at the specified location.
     * @roseuid 5837CA9A03DD
     */
    public boolean hasGhost(GridLocation location) {
        return (this.ghosts.get(location) != null);
    }

    /**
     * Determines if there a free case for a ghost at the specified location.
     *
     * @param location Location to verify if there is a free case for a ghost
     *
     * @return A boolean indicating if there is a free case for a ghost at the specified location.
     * @roseuid 5837CA9A03AB
     */
    public boolean noGhost(GridLocation location) {
        return (this.grid.getCases()[location.x][location.y] == CASE_TYPES.ROAD && this.ghosts.get(location) == null);
    }

    /**
     * Makes a game turn. A turn consists of ghosts moving, towers shooting, the player earning money and losing life
     * points, etc.
     * @roseuid 5837CA9A76FD
     */
    public void makeTurn() {

        this.attackedGhosts.clear();

        for (Ghost ghost : this.ghosts.values()) {
            ghost.makeTurn();
        }

        this.moveGhosts();
        this.addNewGhosts();
        this.attackGhosts();
        this.removeDeadGhosts();

        if (this.ghosts.size() == 0 && this.ghostsReleased == Game.GHOSTS_PER_WAVE) {
            this.endTurn();
        }

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Attack the ghosts with the towers on the grid.
     * @roseuid 5837CA9A98CC
     */
    private synchronized void attackGhosts() {
        // Towers attacking if the turn is not over.
        GridLocation attackedLocation = null;
        for (Tower tower : this.towers.values()) {
            ArrayList<Ghost> aliveGhosts = new ArrayList<Ghost>();
            for (Ghost ghost : this.ghosts.values()) {
                if (!ghost.isDead()) {
                    aliveGhosts.add(ghost);
                }
            }
            attackedLocation = tower.attack(aliveGhosts, this.grid.exitPoint());
            if (attackedLocation != null) {
                this.attackedGhosts.add(attackedLocation);
            }
        }

    }

    /**
     * Add new ghosts on the grid, coming from the entry point.
     * @roseuid 5837CA9BCDAA
     */
    private synchronized void addNewGhosts() {
        if (Game.GHOSTS_PER_WAVE > this.ghostsReleased) {

            GridLocation start = this.shortestPath.getShortestPath().get(0);

            // This means a ghost is blocking the entry.
            if (this.ghosts.get(start) != null) {
                return;
            }

            Ghost critty = new Ghost(start, this.wave);
            this.addGhost(critty);
            this.ghostsReleased++;

        }
    }

    /**
     * Move the ghosts forward on the grid.
     * @roseuid 5837CA9A99AD
     */
    private synchronized void moveGhosts() {
        ArrayList<GridLocation> shortestPath = this.shortestPath.getShortestPath();

        // We go through the shortest path in reverse order. This is
        // to make sure that moving a ghost forward does not overwrite
        // another ghost that was on the next location on the path.
        for (int i = shortestPath.size() - 1; i >= 0; i--) {

            GridLocation pathLocation = shortestPath.get(i);
            Ghost ghost = this.ghosts.get(pathLocation);

            if (ghost == null || !ghost.shouldMove()) {
                continue;
            }
            ghost.move();

            GridLocation nextLocation = this.shortestPath.getNextLocation(ghost.gridLocation);
            if (nextLocation != null) {
                if (deadCount == this.lives){
                }
                else {
                    deadCount--;
                }
            }

            // The ghost has reached the exit!
            if (nextLocation == null) {
                this.ghosts.remove(ghost.gridLocation);
                this.lives--;
                // There is another location the ghost can move to, and it is free.
            } else if (ghosts.get(nextLocation) == null) {
                this.ghosts.remove(ghost.gridLocation);
                ghost.setLocation(nextLocation);
                this.addGhost(ghost);
            }

        }

    }

    /**
     * Remove the ghosts killed by the towers.
     * @roseuid 5837CA9A00AD
     */
    private synchronized void removeDeadGhosts() {
        HashMap<Point, Ghost> ghosts = new HashMap<Point, Ghost>();
        for (Ghost ghost : this.ghosts.values()) {
            if (!ghost.isDead()) {
                ghosts.put(ghost.gridLocation, ghost);
            } else {
                this.money += ghost.getReward();
                this.killedGhosts++;
            }
        }
        this.ghosts = ghosts;
    }

    /**
     * Get the remaining lives of the player.
     *
     * @return An integer representing the life count of the player.
     * @roseuid 5837BA9A0348
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the remaining lives of the player.
     *
     * @param lives an integer representing the remaining lives of the player.
     * @roseuid 5837BA9A0376
     */

    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Determines if the current game is over. That is, if the player has no more lives.
     *
     * @return true is the game is over, false otherwise.
     * @roseuid 5837BA9A03AH
     */
    public boolean isOver() {
        return this.lives <= 0;
    }

    /**
     * Determines if the player has won the game
     *
     * @return True if the number of waves required wave for winning is reached.
     * @roseuid 5837BA9A0398
     */
    public boolean isWon() {
        return this.wave > Game.WAVES_TO_WIN;
    }

    /**
     * Determines if a current game turn is happening.
     *
     * @return True if the game is in a turn, false otherwise.
     * @roseuid 5837BA9A4AB6
     */
    public boolean isMakingTurn() {
        return this.gameThread != null;
    }

    /**
     * Ends the current game turn.
     * @roseuid 5837BA9A0387
     */
    public void endTurn() {
        if (this.gameThread != null) {
            this.gameThread.stopThread();
            this.gameThread = null;
        }
        this.wave++;
        this.ghosts.clear();
        this.ghostsReleased = 0;
    }

    /**
     * Gets the number of killed ghosts.
     *
     * @return The number of killed ghosts in the game.
     * @roseuid 5837BA9A0AB
     */
    public int getKilledGhosts() {
        return this.killedGhosts;
    }

    /**
     * Gets the current wave.
     *
     * @return The serial number of the current wave.
     * @roseuid 5837BA9A03AD5
     */
    public int getWave() {
        return this.wave;
    }

}
