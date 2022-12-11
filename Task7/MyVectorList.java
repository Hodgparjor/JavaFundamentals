package Task7;

import java.util.*;

public class MyVectorList {
    private int numberOfVectors;
    private ArrayList<MyVector> vectors;
    
    public MyVectorList() {
        this.vectors = new ArrayList<MyVector>();
    }

    public void setNumberOfVectors(int newNumberOfVectors) {
        this.numberOfVectors = newNumberOfVectors;
    }

    public int getNumberOfVectors() {
        return this.numberOfVectors;
    }

    public void readNumberOfVectors(Scanner input) {
        System.out.print("\nEnter the number of vectors: ");
        boolean correctInput = false;
        int temporaryNumberOfVectors = 0;
        while(!correctInput) {
            try {
                temporaryNumberOfVectors = input.nextInt();
                correctInput = true;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input, enter single integer: ");
            }
        }
        setNumberOfVectors(temporaryNumberOfVectors);
    }

    public void readVectors(Scanner input) {
        this.vectors.clear();
        System.out.println("Please enter elements of each vector separated by commas. Press Enter to indicate end of vector.");
        for(int counter = 0; counter < this.numberOfVectors; counter++) {
            System.out.print("  Vector " + (counter + 1) + ": ");
            MyVector tmp = new MyVector(input.next());
            this.vectors.add(tmp);
        }
    }

    public String[] prepareExceptionData(MyVector vector1, MyVector vector2, int vectorIndex) {
        String[] exceptionData = new String[4];
        exceptionData[0] = Integer.toString(vector1.getData().size());
        exceptionData[1] = Integer.toString(vectorIndex + 1);
        exceptionData[2] = Integer.toString(vectorIndex + 2);
        if (vector1.getData().size() < vector2.getData().size()) {
            exceptionData[3] = "smaller";
        } else {
            exceptionData[3] = "bigger";
        }
        return exceptionData;
    }

    public void checkVectorsLengths() throws DifferentVectorsLengthsException {
        for (int vectorIndex = 0; vectorIndex < this.vectors.size() - 1; vectorIndex++) {
            if (this.vectors.get(vectorIndex).getData().size() != this.vectors.get(vectorIndex + 1).getData().size()) {
                String[] exceptionData = prepareExceptionData(this.vectors.get(vectorIndex), this.vectors.get(vectorIndex + 1), vectorIndex);
                throw new DifferentVectorsLengthsException("Vectors have diffrent sizes", exceptionData);
            }
        }
    }

    public MyVector sumVectors() throws DifferentVectorsLengthsException {
        this.checkVectorsLengths();
        ArrayList<Integer> sum = new ArrayList<Integer>();
        for (int elementIndex = 0; elementIndex < this.vectors.get(0).getData().size(); elementIndex++) {
            int elementSum = 0;
            for (int vectorIndex = 0; vectorIndex < this.numberOfVectors; vectorIndex++) {
                elementSum += this.vectors.get(vectorIndex).getData().get(elementIndex);
            }
            sum.add(elementSum);
        }
        MyVector result = new MyVector(sum);
        return result;
    }
}
