package Exercise_5_ACO;

import java.util.List;

/**
 * Created by Max Schrimpf
 */
public class Ant {

    boolean foreward;
    List<Vertex> path;
    int backwardIndex;
    int backwardAmtPheromon;
    Vertex curVertex;

    public void nextIntervall(Graph graph) {
        if(foreward) {
            if(curVertex.isFood()) {
                removeLoopsFromPath();
                evaluatePath();
                backwardIndex = 1;
                graph.addPheromonAt(curVertex.getId(), path.get(1).getId(),backwardAmtPheromon);
                curVertex =  path.get(backwardIndex);
            }

        } else {
            if(curVertex.isNest()) {
                return;
            }
            if(backwardIndex+1 < path.size()) {

            removeLoopsFromPath();
            evaluatePath();
            backwardIndex += 1;
            graph.addPheromonAt(curVertex.getId(), path.get(1).getId(),backwardAmtPheromon);
            curVertex =  path.get(backwardIndex);
            } else {
               throw new RuntimeException("inconsistent state") ;
            }
        }
    }

    private void evaluatePath() {

    }

    private void removeLoopsFromPath(){

    }
}
