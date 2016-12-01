package model.tower;

import java.util.Collection;

import model.Ghost;
import model.GridLocation;

/**
 * Explosion tower is a subclass of the Tower class and can be placed on the grid during the game. This tower will
 * attack nearby ghosts when targeting a ghost.
 *
 * @author SnapDragon
 *
 */
public class ExplosionTower extends Tower {

    /**
     * Default constructor for the ExplosionTower class.
     * @roseuid 5837CC410076
     */
    public ExplosionTower() {
        super();
    }

    /**
     * Constructor for an ExplosionTower placed on the game grid.
     *
     * @param location Location of the tower on the game grid.
     * @roseuid 5837CC410084
     */
    public ExplosionTower(GridLocation gridLocation) {
        super(gridLocation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setDetails() {
        this.name = "Explosion tower";
        this.iconPath = "icons/AncientTower.png";

        this.initialCost = 20;
        this.levelCost = 15;
        this.power = 4;
        this.range = 8;
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
            for (Ghost ghost : ghosts) {
                if (GridLocation.nearby(ghost.gridLocation, ghostToAttack.gridLocation)) {
                    ghost.takeDamage(this.power / 4);
                }
            }
            return ghostToAttack.gridLocation;
        }

        return null;

    }

}
