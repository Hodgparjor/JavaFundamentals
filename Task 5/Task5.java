import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Task5 {
    public static void main(String[] args) {
        processData();
    }

    public static void processData() {
        textFileReader inputFileReader = new textFileReader("Task 5\\InputData.txt");
        textFileWriter outputFileWriter = new textFileWriter("Task 5\\myData.txt");

        MyData previousData = new MyData();
        ArrayList<String> inputLines = inputFileReader.getLines();
        for(int i = 0; i < inputLines.size(); i++) {
            int patternID = findPattern(inputLines.get(i));
            if(patternID != -1) {
                MyData newData = new MyData(inputLines.get(i), patternID);
                if (!areDatesTheSame(newData, previousData)) {
                    outputFileWriter.writeLine(newData.toString());
                    previousData = newData;
                }
            }
        }
        outputFileWriter.closeIO();
        inputFileReader.closeIO();
    }

    public static boolean areDatesTheSame (MyData newData, MyData previousData) {
        return (previousData.getDay() == newData.getDay() && previousData.getMonth() == newData.getMonth()
                && previousData.getYear() == newData.getYear() && previousData.getWeekday().equals(newData.getWeekday()));
    }

    public static int findPattern (String line) {
        String[] possiblePatterns = new String[]{"\\d\\d/\\d\\d/\\d{4}+ \\w", "\\d\\d/\\d/\\d{4}+ \\w",
                                                 "\\d{4}+-\\d\\d-\\d\\d \\w", "\\w \\d\\d.\\d\\d.\\d{4}+"};
        for(int patternID = 0; patternID < possiblePatterns.length; patternID++) {
            Pattern pattern = Pattern.compile(possiblePatterns[patternID]);
            Matcher matcher = pattern.matcher(line);
            boolean matchFound = matcher.find();
            if(matchFound) {
                return patternID;
            }
        }
        System.out.println("Invalid format of expression: " + line);
        return -1;
    }
}

class MyData {
    private int day;
    private int month;
    private int year;
    private String weekday;

    MyData(String date, int patternID) {
        switch(patternID) {
            case 0:
                day = Integer.parseInt(date.substring(0, 2));
                month = Integer.parseInt(date.substring(3, 5));
                year = Integer.parseInt(date.substring(6, 10));
                weekday = date.substring(11);
                break;
            case 1:
                day = Integer.parseInt(date.substring(0, 2));
                month = Integer.parseInt(date.substring(3, 4));
                year = Integer.parseInt(date.substring(5, 9));
                weekday = date.substring(10);
                break;
            case 2:
                day = Integer.parseInt(date.substring(8, 10));
                month = Integer.parseInt(date.substring(5, 7));
                year = Integer.parseInt(date.substring(0, 4));
                weekday = date.substring(11);
                break;
            case 3:
                String[] splittedDate = date.split(" ");
                day = Integer.parseInt(splittedDate[1].substring(0, 2));
                month = Integer.parseInt(splittedDate[1].substring(3, 5));
                year = Integer.parseInt(splittedDate[1].substring(6, 10));
                weekday = splittedDate[0];
                break;
        }
    }

    MyData() {
        day = 0;
        month = 0;
        year = 0;
        weekday = "";
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getWeekday() {
        return weekday;
    }

    public String toString() {
        return "day = " + day + ", month = " + month + ", year = " + year +", weekday = " + weekday +".";
    }
}

class textFileReader {
    private static BufferedReader reader;
    textFileReader(String filePathName) {
        try {
            if(filePathName != null) {
                File file = new File(filePathName);
                reader = new BufferedReader(new FileReader(file));
            }
            
        } catch(IOException outputFileError) {
            System.err.println("input file IO error");
            outputFileError.printStackTrace();
        }
    }

    public ArrayList<String> getLines() {
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException inputFileError) {
            System.err.println("input file IO error");
            inputFileError.printStackTrace();
        }
        return lines;
    }

    public void closeIO() {
        try{
            reader.close();
        } catch (IOException fileClosingError) {
            System.err.println("error when closing file");
            fileClosingError.printStackTrace();
        }
    }
}

class textFileWriter {
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