package model.strategy;

import java.util.ArrayList;
import java.util.Collection;

import model.Ghost;
import model.GridLocation;
import model.tower.Tower;

/**
 * Random strategy. Returns a random ghost in range.
 *
 * @author SnapDragon
 *
 */
public class AttackStrategy {

    /**
     * Selects a ghost to attack.
     *
     * @param towerLocation Location of the tower that intends to shoot.
     * @param ghosts ghosts that are currently on the grid.
     * @return The ghost that the tower should shoot according to the strategy.
     * @roseuid 5837CC410087
     */
	public Ghost attackGhost(Tower tower, Collection<Ghost> ghosts, GridLocation endPoint) {

        ArrayList<Ghost> ghostsInRange = new ArrayList<Ghost>();

        for (Ghost ghost : ghosts) {
            if (GridLocation.distance(tower.getLocation(), ghost.gridLocation) <= tower.getRange()) {
                ghostsInRange.add(ghost);
            }
        }

        if (ghostsInRange.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * ghostsInRange.size());
        return ghostsInRange.get(randomIndex);
    }

    /**
     * Gets the name of the attack strategy.
     *
     * @return The name of the attack strategy.
     * @roseuid 5837CC465IEC
     */
	  public String getName() {
		  return "random";
	  }
}
