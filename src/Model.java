import java.util.*;
public class Model {
    private ArrayList<Creature> creatures;
    private MapModel mapModel;
    private View view;

    public Model(int x, int y, int z, View view){
        this.view = view;
        creatures = new ArrayList<>();
        mapModel = new MapModel(x, y, z, creatures, this.view);
    }

    public MapModel getMapModel() {
        return mapModel;
    }
}