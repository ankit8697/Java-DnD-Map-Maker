import java.util.*;
import java.lang.Math;
public class Model {
    private ArrayList<Creature> creatures;
    private MapModel mapModel;

    public Model(int x, int y, int z){
        creatures = new ArrayList<>();
        mapModel = new MapModel(creatures, x, y, z);
    }

    public MapModel getMapModel() {
        return mapModel;
    }
}