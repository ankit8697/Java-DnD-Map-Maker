import java.util.*;
public class Model {
    private ArrayList<Creature> creatures;
    private MapModel mapModel;
    private View view;

    /**
     * @param x The x coordinate of the size of the map
     * @param y The y coordinate of the size of the map
     * @param z The z coordinate of the size of the map
     * @param view The main view that has all the other views
     */
    public Model(int x, int y, int z, View view){
        this.view = view;
        creatures = new ArrayList<>();
        mapModel = new MapModel(x, y, z, creatures, this.view);
    }

    public MapModel getMapModel() {
        return mapModel;
    }
}