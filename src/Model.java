import Creature;
import Terrain;
public class Model {

    public class MapModel {

        private class Cube {
            private int[] coordinates; // bottom-left-back corner of the cube
            private int sideLength;
            private List<Creature> listOfCreatures; // List of all the creatures currently inside of the cube
            private List<Terrain> listOfTerrain; // any current terrain effects within the cube

        }
    }

}