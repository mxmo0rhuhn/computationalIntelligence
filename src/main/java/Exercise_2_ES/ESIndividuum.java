package Exercise_2_ES;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Schrimpf
 */
public class ESIndividuum implements Comparable {

    public static final int D_LEN = 5;

    public static final int H_LEN = 5;

    private final List<Float> object_vector;
    private final List<Float> strategy_vector;

    private final float d;
    private final float h;

    private final float f;
    private final float g;

    public ESIndividuum(List<Float> object_vector, List<Float> strategy_vector) {
        this.strategy_vector = strategy_vector;
        this.object_vector = object_vector;

        if(object_vector.size() != 2 ){
            this.d = 0;
            this.h = 0;

            throw new IllegalArgumentException("invalid number of elements in object vector: " + object_vector.size() );
        }

        this.d = object_vector.get(0);
        this.h = object_vector.get(1);

        this.f = new Float(((Math.PI * Math.pow(this.d,2)) / 2) + Math.PI * this.d * this.h);
        this.g = new Float((Math.PI * Math.pow(this.d,2) * this.h) / 4);
    }

    public ESIndividuum(String individuum, List<Float> strategy_vector) {
        this.strategy_vector = strategy_vector;
        this.object_vector = new ArrayList<Float>();

        if(individuum.length() > D_LEN + H_LEN) {
            this.d = 0;
            this.h = 0;

            throw new IllegalArgumentException("invalid len: " + individuum.length() );
        } else {
        this.d = getRealRepresentation(individuum.substring(0, ESIndividuum.D_LEN));
        this.h = getRealRepresentation(individuum.substring(ESIndividuum.D_LEN, H_LEN+D_LEN));
        }

        this.object_vector.add(this.d);
        this.object_vector.add(this.h);

        this.f = new Float(((Math.PI * Math.pow(this.d,2)) / 2) + Math.PI * this.d * this.h);
        this.g = new Float((Math.PI * Math.pow(this.d,2) * this.h) / 4);
    }

    public ESIndividuum(float d, float h, List<Float> strategy_vector) {
        this.strategy_vector = strategy_vector;
        this.object_vector = new ArrayList<Float>();
        this.d = d;
        this.h = h;

        this.object_vector.add(this.d);
        this.object_vector.add(this.h);

        this.f = new Float(((Math.PI * Math.pow(this.d,2)) / 2) + Math.PI * this.d * this.h);
        this.g = new Float((Math.PI * Math.pow(this.d,2) * this.h) / 4);
    }
    public ESIndividuum(String d, String h, List<Float> strategy_vector) {
        this.strategy_vector = strategy_vector;
        this.object_vector = new ArrayList<Float>();

        if(d.length() > D_LEN || h.length() > H_LEN) {
            this.d = 0;
            this.h = 0;

            throw new IllegalArgumentException("invalid len d: " + d.length() + " > " + D_LEN + " or h: " +h.length() + " > " + H_LEN);
        } else {
            this.d = getRealRepresentation(d);
            this.h = getRealRepresentation(h);
        }

        this.object_vector.add(this.d);
        this.object_vector.add(this.h);

        this.f = new Float(((Math.PI * Math.pow(this.d,2)) / 2) + Math.PI * this.d * this.h);
        this.g = new Float((Math.PI * Math.pow(this.d,2) * this.h) / 4);
    }

    @Override
    public String toString() {
        return "d = " + d + "h = " + h + " strategy = " + strategy_vector.toString();
    }

    public String toExtendedSting() {
        return "function value = " + f + " constraint value = " + g + " d = " + d + "h = " + h + " strategy = " + strategy_vector.toString();
    }

    public int getRealRepresentation(String f) {
        int intBits = Integer.parseInt(f, 2);
        return intBits;
    }

    public float getFunctionValue(){
        return f;
    }


    public boolean satisfiesConstraint(){
        return g >= 300;
    }

    /**
    * return zero if both objects are equal
    * return negative if object is worse than specified one
    * return positive if object is better than specified one
    */

    @Override
    public int compareTo(Object o) {
        ESIndividuum other = (ESIndividuum) o;

        if(!this.satisfiesConstraint() && !other.satisfiesConstraint()) {
            // Both don't satisfy the constraint
            return 0;

        } else if(!this.satisfiesConstraint() && other.satisfiesConstraint()) {
            // this individuum dosn't satisfy constraint
            return -1;

        } else if(this.satisfiesConstraint() && !other.satisfiesConstraint()) {
            // other individuum dosn't satisfy constraint
            return 1;
        }

        if( this.getFunctionValue() > other.getFunctionValue()) {
            return -1;
        } else if( this.getFunctionValue() < other.getFunctionValue()) {
            return 1;
        }
        return 0;
    }

    public List<Float> getObjectVector() {
        return object_vector;
    }
    public List<Float> getStrategyVector() {
        return strategy_vector;
    }
}
