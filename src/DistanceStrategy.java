/**
 * This interface uses the Strategy pattern to easily swap between Euclidean and D&D distance
 */
public interface DistanceStrategy {

    double distance(Cube cube, int x, int y, int z);
}
