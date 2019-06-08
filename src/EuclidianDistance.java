public class EuclidianDistance implements DistanceStrategy {

    public double distance(Cube cube, int x, int y, int z) {
        return cube.getEuclideanDistanceToLocation(x,y,z);
    }
}
