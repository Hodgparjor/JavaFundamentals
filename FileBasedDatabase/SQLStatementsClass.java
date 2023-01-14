package FileBasedDatabase;
import java.util.*;

abstract class SQLStatement {
    protected String statementType;
    protected String tableName;

    public abstract void executeStatement();
}

class SelectStatement extends SQLStatement {
    private String columns;
    private String conditions;

    public SelectStatement(String tableName, String columns, String conditions) {
        this.statementType = "SELECT";
        this.tableName = tableName;
        this.columns = columns;
        this.conditions = conditions;
    }

    ArrayList<Integer> matchIndexes(String[] inputColumns, String[] tableColumns) {
        ArrayList<Integer> matchingIndexes = new ArrayList<Integer>();
        for (int indexInput = 0; indexInput < inputColumns.length; indexInput++) {
            for (int indexTable = 0; indexTable < tableColumns.length; indexTable++) {
                if (inputColumns[indexInput].equals(tableColumns[indexTable])) {
                    matchingIndexes.add(indexTable);
                }
            }
        }
        return matchingIndexes;
    }

    @Override
    public void executeStatement() {
        // System.out.println("SELECT executed:\n  Name:" + this.tableName + "\n  Columns:" + this.columns);
        // if(this.conditions != null) {
        //     System.out.print("  Conditions:" + this.conditions);
        // }
        // System.out.println("\n");

        textFileReader reader = new textFileReader("FileBasedDatabase\\table.txt");
        ArrayList<String> tableLines = reader.getLines();
        if (tableLines.size() == 0) {
            System.out.println("Error: Table is empty.");
            return;
        }

        String[] columnsSplited = this.columns.split(",");
        String[] tableHeaders = tableLines.get(0).split(",");
        ArrayList<Integer> matchingIndexes = matchIndexes(columnsSplited, tableHeaders);

        for(String line : tableLines) {
            String[] splitedLine = line.split(",");
            StringBuilder outputString = new StringBuilder();
            outputString.append("\n");
            for(int index : matchingIndexes) {
                outputString.append(splitedLine[index].trim()).append(",");
            }
            outputString.setLength(outputString.length() - 1);
            System.out.print(outputString.toString());
        }
        System.out.println("\n");
    }
}

class InsertStatement extends SQLStatement {
    private String values;

    public InsertStatement(String tableName, String values) {
        this.statementType = "INSERT";
        this.tableName = tableName;
        this.values = values;
    }

    @Override
    public void executeStatement() {
        textFileReader reader = new textFileReader("FileBasedDatabase\\table.txt");
        String[] tableHeaders = reader.getLines().get(0).split(",");
        reader.closeIO();

        textFileWriter writer = new textFileWriter("FileBasedDatabase\\table.txt");

        String[] valuesSplited = this.values.split(",");
        
        if (valuesSplited.length != tableHeaders.length) {
            System.out.println("Inserted data has diffrent amount of elements than headers.");
            return;
        }

        StringBuilder insertString = new StringBuilder();
        insertString.append("\n");
        for (String value : valuesSplited) {
            insertString.append(value.trim()).append(",");
        }
        insertString.setLength(insertString.length() - 1);
        writer.writeLine(insertString.toString());
        writer.closeIO();
    }
    
}

class UpdateStatement extends SQLStatement {
    private String updates;
    private String conditions;

    public UpdateStatement(String tableName, String updates, String conditions) {
        this.statementType = "UPDATE";
        this.tableName = tableName;
        this.updates = updates;
        this.conditions = conditions;
    }

    @Override
    public void executeStatement() {}
}

class DeleteStatement extends SQLStatement {
    private String conditions;

    public DeleteStatement(String tableName, String conditions) {
        this.statementType = "DELETE";
        this.tableName = tableName;
        this.conditions = conditions;
    }

    @Override
    public void executeStatement() {}
}