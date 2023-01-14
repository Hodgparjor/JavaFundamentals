package Task7;

import java.util.*;
import java.io.*;

public class VectorsAddition {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        MyVectorList vectorList = new MyVectorList();
        vectorList.readNumberOfVectors(input);
        vectorList.readVectors(input);
        boolean correctVectors = false;
        while(!correctVectors){
            try {
                MyVector sum = vectorList.sumVectors();
                System.out.println("Resultant vector is: <" + sum.getVectorAsString() + ">");
                textFileWriter outputToFile = new textFileWriter("Task7\\VectorsAdditionResult.txt");
                outputToFile.writeLine(sum.getVectorAsString());
                outputToFile.closeIO();
                correctVectors = true;
                input.close();
            } catch (DifferentVectorsLengthsException e) {
                System.out.println("\n" + e.getMessage());
                for (int index = 0; index < e.getVectorLengths().size(); index++) {
                    System.out.println("Length of vector " + (index + 1) + " is: " + e.getVectorLengths().get(index));
                }
                vectorList.readVectors(input);
            }
        }
    }

    static class textFileWriter {
        private static PrintWriter writer;
    
        textFileWriter(String filePathName) {
            try {
                if(filePathName != null) {
                    File file = new File(filePathName);
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    writer = new PrintWriter(bw);
                }
            } catch(IOException outputFileError) {
                System.err.println("Output file error");
                outputFileError.printStackTrace();
            }
        }
    
        public void writeLine (String newLine) {
            writer.println(newLine);
        }
    
        public void closeIO() {
            writer.close();
        }
    }
}

