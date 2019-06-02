import java.util.*;
import java.lang.Math.*;
public class Model {

    public class MapModel {
        public static final int DEFAULT_SIDE_LENGTH = 5;

        public ArrayList<ArrayList<ArrayList<Cube>>> map;
        public ArrayList<Terrain> terrains;
        public ArrayList<Creature> creatures;
        int[] dimensions;

        public MapModel(int x, int y, int z) {
            this.terrains =  new ArrayList<>();
            this.creatures =  new ArrayList<>();
            this.map = new ArrayList<ArrayList<ArrayList<Cube>>>();
            int[] dimensionsHolder = {x,y,z};
            this.dimensions = dimensionsHolder;
            ArrayList<ArrayList<Cube>> plane = new ArrayList<ArrayList<Cube>>();
            for(int yCoord = 0 ; yCoord < y ; yCoord++) {
                ArrayList<Cube> row = new ArrayList<Cube>();
                for(int xCoord = 0 ; xCoord < x ; xCoord++) {
                    row.add(new Cube(xCoord, yCoord, z));
                }
                plane.add(row);
            }
            map.add(plane);
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
    }
}