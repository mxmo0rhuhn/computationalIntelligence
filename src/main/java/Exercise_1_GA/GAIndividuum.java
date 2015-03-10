package Exercise_1_GA;

import Helper.Individuum;

/**
 * Created by Max Schrimpf
 */
public class GAIndividuum implements Comparable, Individuum{

    private static final int D_MIN = 0;
    private static final int D_MAX = 31;
    private static final int DELTA_D = 1;
    public static final int D_LEN = 5;

    private static final int H_MIN = 0;
    private static final int H_MAX = 31;
    private static final int DELTA_H = 1;
    public static final int H_LEN = 5;

    private final int d;
    private final int h;

    private final float f;
    private final float g;

    public GAIndividuum(String individuum) {
        if(individuum.length() > D_LEN + H_LEN) {
            this.d = 0;
            this.h = 0;

            throw new IllegalArgumentException("invalid len: " + individuum.length() );
        } else {
        this.d = getRealRepresentation(individuum.substring(0, GAIndividuum.D_LEN));
        this.h = getRealRepresentation(individuum.substring(GAIndividuum.D_LEN, H_LEN+D_LEN));
        }

        this.f = new Float(((Math.PI * Math.pow(this.d,2)) / 2) + Math.PI * this.d * this.h);
        this.g = new Float((Math.PI * Math.pow(this.d,2) * this.h) / 4);
    }

    public GAIndividuum(String d, String h) {

        if(d.length() > D_LEN || h.length() > H_LEN) {
            this.d = 0;
            this.h = 0;

            throw new IllegalArgumentException("invalid len d: " + d.length() + " > " + D_LEN + " or h: " +h.length() + " > " + H_LEN);
        } else {
            this.d = getRealRepresentation(d);
            this.h = getRealRepresentation(h);
        }

        this.f = new Float(((Math.PI * Math.pow(this.d,2)) / 2) + Math.PI * this.d * this.h);
        this.g = new Float((Math.PI * Math.pow(this.d,2) * this.h) / 4);
    }

    @Override
    public String toString() {
        return getBinaryRepresentation(d, D_LEN) + getBinaryRepresentation(h, H_LEN);
    }

    @Override
    public String toExtendedString() {
        return "f = " + f + " g = " + g + " d = " + d + "h = " + h ;
    }

    public int getRealRepresentation(String f) {
        int intBits = Integer.parseInt(f, 2);
        return intBits;
    }

    public int getBinaryRepresentationLength() {
        return D_LEN + H_LEN;
    }
    public String getBinaryRepresentation(int f, int maxLen) {

        String binary = Integer.toBinaryString(f);

        if(binary.length() > maxLen) {
            throw new IllegalStateException("binary representation to long");
        } else if (binary.length() < maxLen) {
            int toFill = maxLen - binary.length();
            char[] charArray = new char[toFill];

            for (int i = 0; i < toFill; i++) {
                charArray[i] = '0';
            }
            binary = String.valueOf(charArray) + binary;
        }
        return binary;
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
        GAIndividuum other = (GAIndividuum) o;

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

    public int getH() {
        return h;
    }
    public int getD() {
        return d;
    }

    public float getConstraintValue() {
        return g;
    }

    @Override
    public float getFValue() {
        return f;
    }

    @Override
    public float getGValue() {
        return g;
    }

    public static int getMaxF() {

        return Math.round(new Float(((Math.PI * Math.pow(D_MAX,2)) / 2) + Math.PI * D_MAX * H_MAX));
    }

    public static int getMaxG() {
        return Math.round(new Float((Math.PI * Math.pow(D_MAX, 2) * H_MAX) / 4));
    }


}
