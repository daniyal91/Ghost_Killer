package model.tower;

import java.util.Collection;

import model.Ghost;
import model.GridLocation;

/**
 * Fire tower is a subclass of the Tower class and can be placed on the grid during the game.
 *
 * @author SnapDragon
 *
 */
public class FireTower extends Tower {

    /**
     * Default constructor for the FireTower class.
     * @roseuid 5837CC410045
     */
    public FireTower() {
        super();
    }

    /**
     * Constructor for a FireTower placed on the game grid.
     *
     * @param location Location of the tower on the game grid.
     * @roseuid 5837CC4106EC
     */
    public FireTower(GridLocation gridLocation) {
        super(gridLocation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setDetails() {
        this.name = "Fire tower";
        this.iconPath = "icons/KingTower.png";

        this.initialCost = 10;
        this.levelCost = 8;
        this.power = 2;
        this.range = 5;
        this.rateOfFire = 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GridLocation attack(Collection<Ghost> ghosts, GridLocation endPoint) {

        Ghost ghostToAttack = this.attackStrategy.attackGhost(this, ghosts, endPoint);

        if (ghostToAttack != null) {
            ghostToAttack.takeDamage(this.power);
            return ghostToAttack.gridLocation;
        }

        return null;

    }


}
