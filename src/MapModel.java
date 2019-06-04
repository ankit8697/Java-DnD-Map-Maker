import org.w3c.dom.css.RGBColor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.lang.Math;

public class MapModel { // this is a comment
    public static final int DEFAULT_SIDE_LENGTH = 5;

    private ArrayList<ArrayList<ArrayList<Cube>>> map;
    private ArrayList<Terrain> terrains;
    private ArrayList<Creature> creatures;
    private int[] dimensions;
    DistanceStrategy distanceType;


    public MapModel(ArrayList<Creature> creatures, int x, int y, int z) {
        distanceType = new DNDDistance();
        this.terrains =  new ArrayList<>();
        this.creatures =  creatures;
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

    public ArrayList<Cube> getCircle(int r, int[] coordinates) {
        int reqRadius = r/5;
        ArrayList<Cube> cubes = new ArrayList<>();
        for (int x = -reqRadius; x <= reqRadius; x++) {
            if (!(x > dimensions[0] || x < 0)) {
                for (int y = -reqRadius; y <= reqRadius; y++) {
                    if (!(y > dimensions[1] || y < 0)) {
                        Cube currentCube = getCube(coordinates[0] + x, coordinates[1] + y, coordinates[2]);
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


    public ArrayList<Cube> getSphere(int r, int[] coordinates) {
        int reqRadius = r/5;
        ArrayList<Cube> cubes = new ArrayList<>();
        for (int x = -reqRadius; x <= reqRadius; x++) {
            if (!(x > dimensions[0] || x < 0)) {
                for (int y = -reqRadius; y <= reqRadius; y++) {
                    if (!(y > dimensions[1] || y < 0)) {
                        for (int z = -reqRadius; z <= reqRadius; z++) {
                            if (!(z > dimensions[2] || z < 0)) {
                                Cube currentCube = getCube(coordinates[0]+x, coordinates[1]+y, coordinates[2]+z);
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

    public ArrayList<Cube> getCylinder(int r, int[] coordinates) {
        ArrayList<Cube> baseCubes = getCircle(r, coordinates);
        ArrayList<Cube> cubes = new ArrayList<>();
        for (Cube cube : baseCubes) {
            ArrayList<Cube> column = getFullColumn(cube.getCoordinates()[0], cube.getCoordinates()[1]);
            cubes.addAll(column);
        }
        return cubes;
    }

    public void moveCreature(Creature aCreature, Cube destCube){
        aCreature.setCurrentLocation(destCube);
    }

    public void moveTerrain(ArrayList<Cube> destCubes, Terrain terrainToMove){
        terrainToMove.setCurrentLocations(destCubes);
    }

    public void addTerrainToCubes(ArrayList<Cube> newCubes, Terrain terrainToAdd){
        for(Cube cube: newCubes){
            terrainToAdd.addToCurrentLocations(cube);
        }
    }

    public void removeTerrainFromCubes(ArrayList<Cube> oldCubes, Terrain terrainToRemove){
        for(Cube cube: oldCubes){
            terrainToRemove.removeFromCurrentLocations(cube);
        }
    }

    public ArrayList<RGBColor> getTerrainColorsInCube(Cube cube){
        List<Terrain> terrainInCube = cube.getListOfTerrain();
        List<RGBColor> terrainColors = new ArrayList<RGBColor>();
        for(Terrain terrain: terrainInCube){
            RGBColor color = terrain.getColor();
            terrainColors.add(color);
        }
    }
}
