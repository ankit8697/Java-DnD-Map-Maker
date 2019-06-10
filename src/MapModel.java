import java.util.ArrayList;
import java.util.List;

public class MapModel {
    public static final int DEFAULT_SIDE_LENGTH = 5;

    private ArrayList<ArrayList<ArrayList<Cube>>> map;
    private ArrayList<Terrain> terrains;
    private ArrayList<Creature> creatures;
    private int[] dimensions;
    DistanceStrategy distanceType;
    View view;


    /**
     * @param x The x coordinate of the map
     * @param y The y coordinate of the map
     * @param z The z coordinate of the map
     * @param creatures The list of creatures contained in the map
     * @param view The main View that contains all the views
     */
    public MapModel(int x, int y, int z, ArrayList<Creature> creatures, View view) {
        distanceType = new DNDDistance();
        this.terrains =  new ArrayList<>();
        this.creatures =  creatures;
        this.map = new ArrayList<>();
        this.view = view;
        int[] dimensionsHolder = {x,y,z};
        this.dimensions = dimensionsHolder;
        ArrayList<ArrayList<ArrayList<Cube>>> plane = new ArrayList<>();
        for(int xCoord = 0 ; xCoord < x ; xCoord++) {
            ArrayList<ArrayList<Cube>> row = new ArrayList<>();
            for(int yCoord = 0 ; yCoord < y ; yCoord++) {
                ArrayList<Cube> item = new ArrayList<>();
                item.add(new Cube(xCoord, yCoord, 0));
                row.add(item);
            }
            plane.add(row);
        }
        this.map = plane;
    }

    /**
     * @param x The x coordinate of the map
     * @param y The y coordinate of the map
     * @param z The z coordinate of the map
     */
    public MapModel(int x, int y, int z) {
        distanceType = new DNDDistance();
        this.terrains =  new ArrayList<>();
        this.creatures =  new ArrayList<>();
        this.map = new ArrayList<>();
        int[] dimensionsHolder = {x,y,z};
        this.dimensions = dimensionsHolder;
        ArrayList<ArrayList<ArrayList<Cube>>> plane = new ArrayList<>();
        for(int xCoord = 0 ; xCoord < x ; xCoord++) {
            ArrayList<ArrayList<Cube>> row = new ArrayList<>();
            for(int yCoord = 0 ; yCoord < y ; yCoord++) {
                ArrayList<Cube> item = new ArrayList<>();
                item.add(makeCube(xCoord, yCoord, z));
                row.add(item);
            }
            plane.add(row);
        }
        this.map = plane;
    }

    /**
     * @param terrain The terrain to be added to the cubes
     * @param cubes The list of cubes to which the terrain must be added
     */
    public void addTerrain(Terrain terrain, List<Cube> cubes) {
        for(Cube cube : cubes) {
            terrain.addToCurrentLocations(cube);
        }
        addTerrain(terrain);
    }

    /**
     * @param terrain The terrain to be added to the list of terrains in the map
     */
    public void addTerrain(Terrain terrain) {
        terrains.add(terrain);
        view.getMapView().updateKey(terrains);
    }

    /**
     * @param terrain The tile to be removed from the list of terrains
     */
    public void removeTerrain(Terrain terrain) {
        terrains.remove(terrain);
        terrain.delete();
        view.getMapView().updateKey(terrains);
    }

    /**
     * @param creature The creature to be moved
     * @param cube The cube to which the creature must be moved to
     */
    public void moveCreature(Creature creature, Cube cube) {
        creature.setCurrentLocation(cube);
    }

    /**
     * @param creature The creature to be added to the list of creatures
     */
    public void addCreatureToList(Creature creature) {
        creatures.add(creature);
    }

    /**
     * @param creature The creature to be deleted from the map
     */
    public void deleteCreature(Creature creature) {
        creature.delete();
        creatures.remove(creature);
    }

    /**
     * @param x x coordinate of the selected column
     * @param y y coordinate of the column
     * @return An arraylist of creatures that are in the selected column
     */
    public ArrayList<Creature> getCreaturesInColumn(int x, int y) {
        ArrayList<Cube> column = map.get(x).get(y);
        ArrayList<Creature> creatures = new ArrayList<>();
        for (Cube cube : column) {
            if (cube != null) {
                for (Creature creature : cube.getListOfCreatures()) {
                    creatures.add(creature);
                }
            }
        }
        return creatures;
    }

    /**
     * @param x x coordinate of the cube
     * @param y y coordinate of the cube
     * @param z z coordinate of the cube
     * @return The cube at the desired coordinates
     * @throws IndexOutOfBoundsException
     */
    public Cube getCube(int x, int y, int z) throws IndexOutOfBoundsException {
        if (x > dimensions[0]-1 || y > dimensions[1]-1 || z > dimensions[2]-1 ||
            x < 0 || y < 0 || z < 0) {
            throw new IndexOutOfBoundsException();
        }

        ArrayList<Cube> column = map.get(x).get(y);
        int maxHeight = column.size();
        if (z < maxHeight) {
            Cube output = column.get(z);
            if (output == null) {
                output = makeCube(x, y, z);
                column.set(z, output);
            }
            return output;
        }
        else {
            for (int i = maxHeight; i < z; i++) {
                column.add(null);
            }
            Cube newCube = makeCube(x, y, z);
            column.add(newCube);
            return newCube;
        }
    }

    private Cube makeCube(int x, int y, int z) {
        Cube cube = new Cube(x, y, z);
        List<Cube> column = getColumn(x, y);
        for (Cube testCube : column) {
            if (testCube != null) {
                if (testCube.getTile() != null) {
                    cube.setTile(testCube.getTile());
                }
            }
        }
        return cube;
    }

    /**
     * @param x x coordinate of the desired column
     * @param y y coordinate of the desired column
     * @return An arraylist of cubes in that column
     * @throws IndexOutOfBoundsException
     */
    public ArrayList<Cube> populateAndGetColumn(int x, int y) throws IndexOutOfBoundsException {
        if (x > dimensions[0] || y > dimensions[1] ||
                x < 0 || y < 0) {
            throw new IndexOutOfBoundsException();
        }

        int targetHeight = dimensions[2]-1;
        ArrayList<Cube> column = map.get(x).get(y);
        int currentHeight = column.size();
        for (int i = 0; i < currentHeight; i++) {
            if(column.get(i) == null) {
                column.set(i, makeCube(x,y,i));
            }
        }
        for (int i = currentHeight; i <= targetHeight; i++) {
            column.add(makeCube(x, y, i));
        }
        return column;
    }

    /**
     * @param x x coordinate of the desired column
     * @param y y coordinate of the desired column
     * @return An arraylist of cubes in that column
     * @throws IndexOutOfBoundsException
     */
    public ArrayList<Cube> getColumn(int x, int y) throws IndexOutOfBoundsException {
        if (x > dimensions[0] || y > dimensions[1] ||
                x < 0 || y < 0) {
            throw new IndexOutOfBoundsException();
        }
        return map.get(x).get(y);
    }

    /**
     * @param r Radius of circle
     * @param coordinates Coordinates of center of circle
     * @return ArrayList of cubes in the circle
     */
    public ArrayList<Cube> getCircle(int r, int[] coordinates) {
        int reqRadius = r/5;
        ArrayList<Cube> cubes = new ArrayList<>();
        if (r < 0) {
            return cubes;
        }
        for (int x = -reqRadius; x <= reqRadius; x++) {
            int targetX = coordinates[0] + x;
            if (!(targetX > dimensions[0]-1 || targetX < 0)) {
                for (int y = -reqRadius; y <= reqRadius; y++) {
                    int targetY = coordinates[1] + y;
                    if (!(targetY > dimensions[1]-1 || targetY < 0)) {
                        Cube currentCube = getCube(targetX, targetY, coordinates[2]);
                        double distance = distanceType.distance(currentCube, coordinates[0], coordinates[1], coordinates[2]);
                        if (distance <= (double) r + 2.5) {
                            cubes.add(currentCube);
                        }
                    }
                }
            }
        }
        return cubes;
    }


    /**
     * @param r Radius of sphere
     * @param coordinates Coordinats of center of sphere
     * @return ArrayList of cubes that make up the sphere
     */
    public ArrayList<Cube> getSphere(int r, int[] coordinates) {
        int reqRadius = r/5;
        ArrayList<Cube> cubes = new ArrayList<>();
        if (r < 0) {
            return cubes;
        }
        for (int x = -reqRadius; x <= reqRadius; x++) {
            int targetX = coordinates[0] + x;
            if (!(targetX > dimensions[0]-1 || targetX < 0)) {
                for (int y = -reqRadius; y <= reqRadius; y++) {
                    int targetY = coordinates[1] + y;
                    if (!(targetY > dimensions[1]-1 || targetY < 0)) {
                        for (int z = -reqRadius; z <= reqRadius; z++) {
                            int targetZ = coordinates[2] + z;
                            if (!(targetZ > dimensions[2]-1 || targetZ < 0)) {
                                Cube currentCube = getCube(targetX, targetY, targetZ);
                                double distance = distanceType.distance(currentCube, coordinates[0], coordinates[1], coordinates[2]);
                                if (distance <= (double) r+2.5) {
                                    cubes.add(currentCube);
                                }
                            }
                        }
                    }
                }
            }
        }
        return cubes;
    }

    /**
     * @param r Radius of cylinder
     * @param coordinates Coordinates of center of cylinder
     * @return ArrayList of cubes that constitute the cylinder
     */
    public ArrayList<Cube> getCylinder(int r, int[] coordinates) {
        ArrayList<Cube> baseCubes = getCircle(r, coordinates);
        ArrayList<Cube> cubes = new ArrayList<>();
        for (Cube cube : baseCubes) {
            ArrayList<Cube> column = populateAndGetColumn(cube.getCoordinates()[0], cube.getCoordinates()[1]);
            cubes.addAll(column);
        }
        return cubes;
    }

    public void moveTerrain(ArrayList<Cube> destCubes, Terrain terrainToMove){
        terrainToMove.setCurrentLocations(destCubes);
    }

    /**
     * @param terrainToAdd Terrain to be added
     * @param newCubes List of cubes the terrain must be added to
     */
    public void addTerrainToCubes(Terrain terrainToAdd, List<Cube> newCubes){
        for(Cube cube: newCubes){
            terrainToAdd.addToCurrentLocations(cube);
        }
    }

    public void removeTerrainFromCubes(Terrain terrainToRemove, List<Cube> oldCubes){
        for(Cube cube: oldCubes){
            terrainToRemove.removeFromCurrentLocations(cube);
        }
    }

    public ArrayList<Terrain> getTerrains() {
        return terrains;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public int[] getDimensions() {
        return dimensions;
    }

}
