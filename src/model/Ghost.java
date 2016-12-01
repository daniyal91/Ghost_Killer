package model;

/**
 * Class representing a game ghost.
 *
 * @author SnapDragon
 *
 */
public class Ghost {

    /**
     * Path of the image representing the ghost.
     */
    public static String ICON_PATH = "icons/ghost.jpg";

    /**
     * Value after which the ghost moves.
     */
    public static int MOVEMENT_THRESHOLD = 100;

    /**
     * Initial health points of a new ghost.
     */
    public static int INITIAL_HEALTH_POINTS = 30;

    /**
     * Initial speed of a new ghost.
     */
    public static int INITIAL_SPEED = 70;


    /**
     * Reward for killing the ghost, relative to it's health points.
     */
    public static int REWARD_RATIO = 2;

    /**
     * Additional health points a ghost gains at every level.
     */
    public static int HEALTH_POINTS_PER_LEVEL = 10;

    /**
     * Additional speed a ghost gains at every level.
     */
    public static int SPEED_PER_LEVEL = 10;


    public static int idCount = 0;

    public int ghostID;
    public GridLocation gridLocation;

    private int healthPoints;
    private int level;
    private int movementPoints = 0;

    /**
     * Constructor method for a ghost.
     *
     * @param gridLocation Location of the ghost on the grid.
     * @param level Level of the ghost to create.
     *  @roseuid 5837CC2500AD
     */
    public Ghost(GridLocation gridLocation, int level) {
        this.gridLocation = gridLocation;
        this.healthPoints = Ghost.HEALTH_POINTS_PER_LEVEL * level;
        this.level = level;
        this.ghostID = ++idCount;
    }

    /**
     * Constructor method for a ghost, using an existing ghost instance.
     *
     * @param ghost ghost instance to copy.
     *  @roseuid 5837CC77IA54
     */
    public Ghost(Ghost ghost) {
        this.gridLocation = ghost.gridLocation;
        this.healthPoints = Ghost.INITIAL_HEALTH_POINTS + (Ghost.HEALTH_POINTS_PER_LEVEL * ghost.level);
        this.level = ghost.level;
        this.ghostID = ghost.ghostID;
    }

    /**
     * Indicates whether the ghost is dead or not.
     *
     * @return true if the ghost has any remaining health point, false otherwise.
     *  @roseuid 5837CC250765
     */
    public boolean isDead() {
        return this.healthPoints <= 0;
    }

    /**
     * Attacks the current ghost with the specified damage. The ghost's health can never go below 0.
     *
     * @param damage Damage to deal to the ghost.
     * @param burning Boolean specifying if the damage is burning (if it lasts after the current turn.)
     *  @roseuid 5837CC25005A
     */
    public void takeDamage(int damage) {
        this.healthPoints = this.healthPoints - damage;
    }

    /**
     * Make turn for the ghosts.
     *  @roseuid 5837CC2HUA34
     */
    public void makeTurn() {
        int speed = Ghost.INITIAL_SPEED + (this.level * Ghost.SPEED_PER_LEVEL);
        this.movementPoints += speed;
        this.healthPoints = Math.max(this.healthPoints, 0);
    }

    /**
     * Get the reward associated with killing the ghost.
     *
     * @return An integer representing the reward as money.
     *  @roseuid 5837CC25051A
     */
    public int getReward() {
        return Ghost.INITIAL_HEALTH_POINTS + (Ghost.HEALTH_POINTS_PER_LEVEL * this.level);
    }

    /**
     * Change the location of the ghost.
     *
     * @param newLocation New location of the ghost on the grid.
     *  @roseuid 5837CC25IAC1
     */
    public void setLocation(GridLocation newLocation) {
        this.gridLocation = newLocation;
    }

    /**
     * Gets the health points of the ghost.
     *
     * @return An integer representing the health points of the ghost.
     *  @roseuid 5837CC965FEB
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Gets the level.
     *
     * @return an integer representing the speed
     *  @roseuid 5837CC250FCB
     */

    public int getLevel() {
        return level;
    }

    /**
     * Sets the level.
     *
     * @param level an integer representing the speed
     *  @roseuid 5837CC2BCF43
     */

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String template = "Ghost level %s at position %s with %s remaining health points";
        return String.format(template, this.level, this.gridLocation.toString(), this.healthPoints);
    }

    /**
     * Checks if movement threshold is reached.
     *
     * @return true if ghost can still be moved otherwise false
     *  @roseuid 5837CC753EFC
     */

    public boolean shouldMove() {
        return this.movementPoints >= Ghost.MOVEMENT_THRESHOLD;
    }

    /**
     * Decreases the movement points by movement threshold.
     *  @roseuid 5837CC250009
     */

    public void move() {
        this.movementPoints -= Ghost.MOVEMENT_THRESHOLD;
    }

    /**
     * returns the movement points.
     *
     * @return an integer representing the movement points
     *  @roseuid 5837CC2508I0
     */

    public int getMovementPoints() {
        return this.movementPoints;
    }

    /**
     * Returns the ghost's speed.
     *
     * @return an integer representing the ghost's speed
     *  @roseuid 5837CC25054EA
     */

    public int getSpeed() {
        int speed = Ghost.INITIAL_SPEED + (Ghost.SPEED_PER_LEVEL * this.level);
        return speed;
    }

}
