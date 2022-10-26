import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
/*TODO
 * WIP File handling and logic not in the same function, preferably in other class !!!arrayList for dynamic solution!!!
 * Change names to more aprioprate (convertDate does not convert Dates etc)
 * DONE Modify constructor of MyData, so empty object could be initialised
 */
public class Task5 {
    public static void main(String[] args) {
        processData();
    }

    public static void processData() {
            // File inputFile = new File("InputData.txt");
            // FileWriter fw = new FileWriter("MyData.txt");
            // BufferedWriter bw = new BufferedWriter(fw);
            // PrintWriter outputFile = new PrintWriter(bw);
            // BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        textFileHandler file = new textFileHandler();
        ArrayList<String> inputLines = file.getLines("InputData.txt");
        MyData previousData = new MyData();
        for(int i = 0; i < inputLines.size(); i++) {
            int patternID = findPattern(inputLines.get(i));
            if(patternID != -1) {
                MyData newData = new MyData(inputLines.get(i), patternID);
                if (!areDatesTheSame(newData, previousData)) {
                        // String newDataString = "day = " + newData.getDay() + ", month = " + newData.getMonth() + ", year = "
                        // + newData.getYear() +", weekday = " + newData.getWeekday() +".";
                    textFileHandler.writeLine("myData.txt", newData.toString());
                    previousData = newData;
                }
            }
        }
                
        //         if(patternID != -1) {
        //             MyData newData = new MyData(inputLine, patternID);
                    
        //             }
        //         }
            
        //     reader.close();
        //     outputFile.close();
        // } catch (FileNotFoundException inputFileError) {
        //     System.err.println("Input file not found");
        //     inputFileError.printStackTrace();
        // } catch (IOException outputFileError) {
        //     System.err.println("Output file error");
        //     outputFileError.printStackTrace();
        // }
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

class textFileHandler {
    //private String inputFileName;
    //private String outputFileName;

    public ArrayList<String> getLines(String inputFilePathname) {
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        try {
            File inputFile = new File(inputFilePathname);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (FileNotFoundException inputFileError1) {
            System.err.println("Input file not found");
            inputFileError1.printStackTrace();
        } catch (IOException inputFileError2) {
            System.err.println("input file IO error");
            inputFileError2.printStackTrace();
        } catch (NullPointerException inputFileError3) {
            System.err.println("input file null pointer error");
            inputFileError3.printStackTrace();
        }
        return lines;
    }

    public static void writeLine (String outputFilePathname, String newLine) {
        try {
            FileWriter fw = new FileWriter(outputFilePathname);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter outputFile = new PrintWriter(bw);
            outputFile.println(newLine);
            outputFile.close();
        } catch(IOException outputFileError) {
            System.err.println("Output file error");
            outputFileError.printStackTrace();
        }
    }
}