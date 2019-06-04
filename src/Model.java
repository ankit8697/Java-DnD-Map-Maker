import java.util.*;
import java.lang.Math;
public class Model {
    private ArrayList<Creature> creatures;
    public MapModel mapModel;

    public Model(int x, int y, int z){
        creatures = new ArrayList<>();
        mapModel = new MapModel(creatures, x, y, z);
    }
}