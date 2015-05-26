package Exercise_5_ACO;

/**
 * Created by Max Schrimpf
 */
public class Vertex {
    int id;
    int curAnts;
    boolean nest;
    boolean food;

    public Vertex(int id) {
        this.id = id;
    }

    public int getCurAnts() {
        return curAnts;
    }

    public void setCurAnts(int curAnts) {
        this.curAnts = curAnts;
    }

    public boolean isNest() {
        return nest;
    }

    public void setNest(boolean nest) {
        this.nest = nest;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public int getId() {
        return id;
    }
}
