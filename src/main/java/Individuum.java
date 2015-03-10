/**
 * Created by Max Schrimpf
 */
public class Individuum {

    public static final int D_LEN = 5;
    public static final int D_MAX = 32;
    public static final int D_MIN = 0;
    public static final int H_LEN = 5;
    public static final int H_MAX = 32;
    public static final int H_MIN = 0;

    private final int d;
    private final int h;

    private final float f;
    private final float g;

    public static int getMaxF() {

        return Math.round(new Float(((Math.PI * Math.pow(D_MAX,2)) / 2) + Math.PI * D_MAX * H_MAX));
    }
    public static int getMaxG() {
        return Math.round(new Float((Math.PI * Math.pow(D_MAX, 2) * H_MAX) / 4));
    }
    public Individuum(String individuum) {
        if(individuum.length() > D_LEN + H_LEN) {
            this.d = 0;
            this.h = 0;

            throw new IllegalArgumentException("invalid len: " + individuum.length() );
        } else {
        this.d = getRealRepresentation(individuum.substring(0, Individuum.D_LEN));
        this.h = getRealRepresentation(individuum.substring(Individuum.D_LEN, H_LEN+D_LEN));
        }

        this.f = new Float(((Math.PI * Math.pow(this.d,2)) / 2) + Math.PI * this.d * this.h);
        this.g = new Float((Math.PI * Math.pow(this.d,2) * this.h) / 4);
    }

    public Individuum(String d, String h) {

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

    public String toExtendedString() {
        return  " f: " + this.f + " g: " + this.g + " d = " + this.d + " h = " + this.h;
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

    public float getGValue() {
        return g;
    }
    public float getFValue(){
        return f;
    }
}
