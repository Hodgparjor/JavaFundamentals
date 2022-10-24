import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Task5 {
    public static void main(String[] args) {
        convertDate();
    }

    public static void convertDate() {
        try {
            File inputFile = new File("InputData.txt");
            FileWriter fw = new FileWriter("MyData.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter outputFile = new PrintWriter(bw);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String inputLine;
            MyData previousData = new MyData("", -1);
            while ((inputLine = reader.readLine()) != null) {
                int patternID = findPattern(inputLine);
                if(patternID != -1) {
                    MyData newData = new MyData(inputLine, patternID);
                    if (!areDatesTheSame(newData, previousData)) {
                        outputFile.println("day = " + newData.getDay() + ", month = " + newData.getMonth() + ", year = "
                                           + newData.getYear() +", weekday = " + newData.getWeekday() +".");
                        previousData = newData;
                    }
                }
            }
            reader.close();
            outputFile.close();
        } catch (FileNotFoundException inputFileError) {
            System.out.println("Input file not found");
        } catch (IOException outputFileError) {
            System.out.println("Output file error");
        }
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
            default:
                day = 0;
                month = 0;
                year = 0;
                weekday = "";
        }
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

    void printData() {
        System.out.println("day = " + day + ", month = " + month + ", year = " + year +", weekday = " + weekday +".");
    }
}