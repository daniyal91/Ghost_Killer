package model.tower;

import java.util.Collection;

import model.Ghost;
import model.GridLocation;

/**
 * Ice tower is a subclass of the Tower class and can be placed on the grid during the game. This tower will slow down
 * the ghosts.
 *
 * @author SnapDragon
 *
 */
public class IceTower extends Tower {

    /**
     * Default constructor for the IceTower class.
     * @roseuid 5837CC415ACD
     */
    public IceTower() {
        super();
    }

    /**
     * Constructor for a IceTower placed on the game grid.
     *
     * @param location Location of the tower on the game grid.
     * @roseuid 5837CC409168
     */
    public IceTower(GridLocation gridLocation) {
        super(gridLocation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setDetails() {
        this.name = "Ice tower";
        this.iconPath = "icons/ModernTower.png";

        this.initialCost = 5;
        this.levelCost = 4;
        this.power = 1;
        this.range = 3;
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
