package FileBasedDatabase;
import java.io.*;
import java.util.*;

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