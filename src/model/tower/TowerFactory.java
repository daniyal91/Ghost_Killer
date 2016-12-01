package model.tower;

/**
 * Factory class for creating Tower instances.
 *
 * @author SnapDragon
 *
 */
public class TowerFactory {

    /**
     * Creates the Tower instance associated with the name provided.
     *
     * @param towerName Name of the tower instance to create.
     *
     * @return A new Tower instance.
     * @roseuid 5837CC570338
     */
    public static Tower createTower(String towerName) {
        if (towerName.equals("Fire tower")) {
            return new FireTower();
        } else if (towerName.equals("Ice tower")) {
            return new IceTower();
        } else if (towerName.equals("Explosion tower")) {
            return new ExplosionTower();
        } else {
            throw new IllegalArgumentException("Invalid tower name " + towerName);
        }
    }

}
