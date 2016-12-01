package model.tower;

import java.util.ArrayList;
import java.util.Collection;

import model.Ghost;
import model.GridLocation;
import model.strategy.AttackStrategy;

/**
 * Base class for game towers.
 *
 * @author SnapDragon
 *
 */
public abstract class Tower {

    /**
     * Refund rate of the towers.
     */
    public static double REFUND_RATE = 0.40;

    private static int idCounter = -3;
    private int towerID;

    /**
     * Statistics elements of the tower.
     */
    protected String name;
    protected String iconPath;
    protected int initialCost;
    protected int level = 1;
    protected int levelCost;
    protected int range;
    protected int power;
    protected int rateOfFire;

    /**
     * Attack strategy the tower uses when attacking the ghosts.
     */
    protected AttackStrategy attackStrategy = new AttackStrategy();

    /**
     * Location of the tower, if it is place on the game grid.
     */
    protected GridLocation location;


    /**
     * Default constructor for the Tower class.
     * @roseuid 5837CC570331
     */
    public Tower() {
        this.setDetails();
        towerID = ++idCounter;
    }

    /**
     * Constructor for a Tower placed on the game grid.
     *
     * @param location Location of the tower on the game grid.
     * @roseuid 5837CC57I635
     */
    public Tower(GridLocation location) {
        this.setDetails();
        towerID = ++idCounter;
        this.location = location;
    }

    /**
     * Sets the internal details of the tower.
     * @roseuid 5837CC570IB4
     */
    protected abstract void setDetails();

    /**
     * Causes the tower to attack a ghost.
     *
     * @param ghosts List of ghosts currently on the game grid.
     * @param endPoint End point of the game grid. Might be used in the targeting strategy.
     *
     * @return The location of the ghost that was targeted for attack.
     * @roseuid 5837CC570330
     */
    public abstract GridLocation attack(Collection<Ghost> ghosts, GridLocation endPoint);

    /**
     * Gets the name of the tower.
     * @roseuid 5837CC5706AE
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the id of the tower.
     *
     * @return Integer representing the id of the tower.
     * @roseuid 5837CC5767IA
     */
    public int getTowerID() {
        return towerID;
    }

    /**
     * Gets the path of the icon image used to represent the tower.
     * @roseuid 5837CC5776AC
     *
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Gets the initial cost to buy the tower.
     * @roseuid 5837CC570335
     */
    public int getInitialCost() {
        return initialCost;
    }

    /**
     * Gets the current level of the tower.
     * @roseuid 5837CC5711CD
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the current level of the tower.
     * @roseuid 5837CC57009IB
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the location of the tower.
     *
     * @return Grid location of the tower.
     * @roseuid 5837CIAE0331
     */
    public GridLocation getLocation() {
        return this.location;
    }

    /**
     * Gets the cost to upgrade the level of the tower.
     * @roseuid 5837CC570332
     *
     */
    public int getLevelCost() {
        return levelCost;
    }

    /**
     * Gets the range of attack of the tower.
     * @roseuid 5837CC570IBH
     */
    public int getRange() {
        return range;
    }

    /**
     * Gets the attack power of the tower.
     * @roseuid 5837CC57033G
     */
    public int getPower() {
        return power;
    }

    /**
     * Gets the rate of fire of the tower.
     * @roseuid 5837CC57066G
     */
    public int getRateOfFire() {
        return rateOfFire;
    }

    /**
     * Gets the attack's strategy.
     *
     * @return an object of AttackStrategy class representing the attack strategy
     * @roseuid 5837CC5703G56
     */

    public AttackStrategy getAttackStrategy() {
        return this.attackStrategy;
    }

    /**
     * Sets the attack's strategy.
     *
     * @param attackStrategy an object of AttackStrategy class representing the attack strategy
     * @roseuid 5837CC57065E
     */

    public void setAttackStrategy(AttackStrategy attackStrategy) {
        this.attackStrategy = attackStrategy;
    }

    /**
     * Sets the attack's strategy.
     *
     * @param attackStrategy a string representing the attack's strategy
     * @roseuid 5837CC5703445
     */

//    public void setAttackStrategy(String attackStrategy) {
//        this.attackStrategy = AttackStrategyFactory.createStrategy(attackStrategy);
//    }

    /**
     * Returns a textual representation of the tower.
     */
    @Override
    public String toString() {
        String template = "Tower [%s] (%s)";
        String response = String.format(template, this.towerID, this.name);
        if (this.location != null) {
            response += String.format(" placed at [%s]", this.location.toString());
        }
        return response;
    }

    /**
     * Calculates the refund amount of the tower
     *
     * @return The refund amount of the tower.
     * @roseuid 5837CC570556
     */
    public int refundAmout() {
        int totalCost = this.initialCost + (this.level * this.levelCost);
        return (int) (totalCost * Tower.REFUND_RATE);
    }

    /**
     * Upgrades the level of the tower by 1, and adjust its details accordingly
     * @roseuid 5837CC5703G10
     */
    public void upgradeLevel() {
        this.level++;
        this.power *= 2;
        this.range *= 2;
        this.rateOfFire *= 2;
    }

    /**
     * Sets the location on the grid.
     *
     * @param gridLocation an object of GridLocation class
     * @roseuid 5837CC570G27
     */
    public void setLocation(GridLocation gridLocation) {
        this.location = gridLocation;
    }

    /**
     * Resets the id counter used to generate the
     * unique ids of the towers.
     * @roseuid 5837CC570420
     */
    public static void resetIdCounter() {
        Tower.idCounter = -3;
    }

}
