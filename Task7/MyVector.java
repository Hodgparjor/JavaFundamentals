package Task7;

import java.util.*;

public class MyVector {
    private ArrayList<Integer> data;

    public MyVector() {
        this.data = new ArrayList<Integer>();
    }

    public MyVector(String stringData) {
        this.data = new ArrayList<Integer>();
        this.setDataFromString(stringData);
    }

    public MyVector(ArrayList<Integer> newData) {
        this.data = new ArrayList<Integer>();
        for (int element : newData) {
            this.data.add(element);
        }
    }

    public ArrayList<Integer> getData() {
        return this.data;
    }

    public ArrayList<Integer> setDataFromString(String stringData) {
        String[] splittedElements = stringData.split(",");
        for (String element : splittedElements) {
            try {
                int value = Integer.parseInt(element);
                this.data.add(value);
            } catch (NumberFormatException e) {
                continue;
            }
        }
        return this.data;
    }

    public String getVectorAsString() {
        String vectorString = "";
        for(int element : this.data) {
            vectorString += (Integer.toString(element));
            if(!(this.data.indexOf(element) == this.data.size() - 1)) {
                vectorString += ",";
            }
        }
        return vectorString;
    }
}
