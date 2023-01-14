package Task7;

import java.util.*;

public class DifferentVectorsLengthsException extends Exception {
    private ArrayList<Integer> vectorLengths;

    public DifferentVectorsLengthsException(String message, ArrayList<Integer> vectorLengths) {
        super(message);
        this.vectorLengths = vectorLengths;
    }

    public ArrayList<Integer> getVectorLengths() {
        return this.vectorLengths;
    }
}
