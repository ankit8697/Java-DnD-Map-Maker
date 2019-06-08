public class DNDDistance implements DistanceStrategy {

    public double distance(Cube cube, int x, int y, int z) {
        return cube.getDNDDistanceToLocation(x, y, z);
    }
}
