package model;

import java.awt.Point;

/**
 * This class implements a location on the game grid.
 *
 * @author SnapDragon
 *
 */
public class GridLocation extends Point {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the GridLocation object. Will default to an invalid location (-1, -1).
     * @roseuid 5837CC410049
     */
    public GridLocation() {
        this.x = -1;
        this.y = -1;
    }

    /**
     * Constructs the GridLocation object from a set of coordinates.
     * @roseuid 5837CC4188IB
     */
    public GridLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a textual representation of the GridLocation object.
     * @roseuid 5837CC410887
     */
    @Override
    public String toString() {
        String template = "GridLocation (%s, %s)";
        return String.format(template, this.x, this.y);
    }

    /**
     * Calculates the Manhattan distance between 2 GridLocation objects.
     *
     * @param location1 first GridLocation object
     * @param location2 second GridLocation object
     *
     * @return The Manhattan distance between the 2 objects.
     * @roseuid 5837CC41054E
     */
    public static int distance(GridLocation location1, GridLocation location2) {
        int distanceX = Math.max(location1.x, location2.x) - Math.min(location1.x, location2.x);
        int distanceY = Math.max(location1.y, location2.y) - Math.min(location1.y, location2.y);
        return distanceX + distanceY;
    }

    /**
     * Indication whether or not 2 grid locations are nearby.
     *
     * @param location1 first GridLocation object
     * @param location2 second GridLocation object
     *
     * @return A Boolean indicating if the 2 grid locations are nearby.
     * @roseuid 5837CC4ABC5
     */
    public static boolean nearby(GridLocation location1, GridLocation location2) {
        if (location1.x == location2.x) {
            if (location1.y == location2.y - 1 || location1.y == location2.y + 1) {
                return true;
            }
        } else if (location1.y == location2.y) {
            if (location1.x == location2.x - 1 || location1.x == location2.x + 1) {
                return true;
            }

        }
        return false;
    }

}
