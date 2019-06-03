import java.util.*;
import java.lang.Math;
public class Model {

    public class MapModel { // this is a comment
        public static final int DEFAULT_SIDE_LENGTH = 5;

        public ArrayList<ArrayList<ArrayList<Cube>>> map;
        public ArrayList<Terrain> terrains;
        public ArrayList<Creature> creatures;
        int[] dimensions;

        public MapModel(int x, int y, int z) {
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
                    item.add(new Cube(xCoord, yCoord, z));
                    row.add(item);
                }
                plane.add(row);
            }
            map = plane;
        }

        public void addTerrain(List<Cube> cubes, Terrain terrain) {
            for(Cube cube : cubes) {
                cube.addToListOfTerrain(terrain);
                terrain.addToCurrentLocations(cube);
            }
        }

        public void addCreature(Cube cube, Creature creature) {
            cube.addToListOfCreatures(creature);
            creature.setCurrentLocation(cube);
        }

        public void deleteTerrain(Terrain terrain) {
            terrain.delete();
            terrains.remove(terrain);
        }

        public void deleteCreature(Creature creature) {
            creature.delete();
            creatures.remove(creature);
        }

        public ArrayList<Creature> getCreaturesInColumn(int x, int y) {
            ArrayList<Cube> column = map.get(x).get(y);
            ArrayList<Creature> creatures = new ArrayList<>();
            for (Cube cube : column) {
                for (Creature creature : cube.getListOfCreatures()) {
                    creatures.add(creature);
                }
            }
            return creatures;
        }

        public Cube getCube(int x, int y, int z) throws IndexOutOfBoundsException {
            if (x > dimensions[0] || y > dimensions[1] || z > dimensions[2] ||
                x < 0 || y < 0 || z < 0) {
                throw new IndexOutOfBoundsException();
            }

            ArrayList<Cube> column = map.get(x).get(y);
            int maxHeight = column.size();
            if (z < maxHeight) {
                Cube output = column.get(z);
                if (output == null) {
                    output = new Cube(x, y, z);
                    column.set(z, output);
                }
                return output;
            }
            else {
                for (int i = maxHeight; i < z; i++) {
                    column.add(null);
                }
                Cube newCube = new Cube(x, y, z);
                column.add(newCube);
                return newCube;
            }
        }

        public ArrayList<Cube> getFullColumn(int x, int y) throws IndexOutOfBoundsException {
            if (x > dimensions[0] || y > dimensions[1] ||
                    x < 0 || y < 0) {
                throw new IndexOutOfBoundsException();
            }

            int targetHeight = dimensions[2]+1;
            ArrayList<Cube> column = map.get(x).get(y);
            int currentHeight = column.size();
            for (int i = currentHeight; i <= targetHeight; i++) {
                column.add(new Cube(x, y, i));
            }
            return column;
        }

    }
}