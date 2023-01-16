package FileBasedDatabase;
import java.io.File;
import java.util.*;

abstract class SQLStatement {
    protected String statementType;
    protected String tableName;

    public abstract void executeStatement() throws InvalidSQLStatementException;
}

class SelectStatement extends SQLStatement {
    private String columns;
    private String conditions;
    private static final String FILE_SUFFIX = ".txt";
    private static final String LOCATION = "FileBasedDatabase\\tables\\";

    public SelectStatement(String tableName, String columns, String conditions) {
        this.statementType = "SELECT";
        this.tableName = tableName;
        this.columns = columns;
        this.conditions = conditions;
    }

    ArrayList<Integer> matchIndexes(String[] inputColumns, String[] tableColumns) throws InvalidSQLStatementException {
        ArrayList<Integer> matchingIndexes = new ArrayList<Integer>();
        boolean matchFound;
        for (int indexInput = 0; indexInput < inputColumns.length; indexInput++) {
            matchFound = false;
            for (int indexTable = 0; indexTable < tableColumns.length; indexTable++) {
                if ((inputColumns[indexInput].trim()).equals(tableColumns[indexTable].trim()) || inputColumns[0].equals("*")) {
                    matchingIndexes.add(indexTable);
                    matchFound = true;
                }
            }
            if(!matchFound) {
                throw new InvalidSQLStatementException("Error: Column " + inputColumns[indexInput] + " is not in table headers.", inputColumns[indexInput]);
            }
        }
        return matchingIndexes;
    }

    @Override
    public void executeStatement() throws InvalidSQLStatementException{
        
        textFileReader reader = new textFileReader(LOCATION + this.tableName + FILE_SUFFIX);
        ArrayList<String> tableLines = reader.getLines();

        String[] columnsSplited = this.columns.split(",");
        String[] tableHeaders = tableLines.get(0).split(",");
        ArrayList<Integer> matchingIndexes = matchIndexes(columnsSplited, tableHeaders);
        System.out.print(tableLines.get(0));
        for(int lineIndex = 1; lineIndex < tableLines.size(); lineIndex++) {
            String[] splitedLine = tableLines.get(lineIndex).split(",");
            if(this.conditions == null || WhereKeyword.evaluateCondition(this.conditions, tableHeaders, splitedLine)) {
                StringBuilder outputString = new StringBuilder();
                outputString.append("\n");
                for(int index : matchingIndexes) {
                    outputString.append(splitedLine[index].trim()).append(",");
                }
                outputString.setLength(outputString.length() - 1);
                System.out.print(outputString.toString());
            }
        }
        System.out.println("\n");
    }
}

class InsertStatement extends SQLStatement {
    private String values;
    private static final String FILE_SUFFIX = ".txt";
    private static final String LOCATION = "FileBasedDatabase\\tables\\";

    public InsertStatement(String tableName, String values) {
        this.statementType = "INSERT";
        this.tableName = tableName;
        this.values = values;
    }

    @Override
    public void executeStatement() throws InvalidSQLStatementException{
        textFileReader reader = new textFileReader(LOCATION + this.tableName + FILE_SUFFIX);
        String[] tableHeaders = reader.getLines().get(0).split(",");
        reader.closeIO();

        textFileWriter writer = new textFileWriter(LOCATION + this.tableName + FILE_SUFFIX, true);

        String[] valuesSplited = this.values.split(",");
        
        if (valuesSplited.length != tableHeaders.length) {
            throw new InvalidSQLStatementException("Inserted data has diffrent amount of elements than headers.", "Inserted has " + valuesSplited.length + "table has" + tableHeaders.length);
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
    private static final String FILE_SUFFIX = ".txt";
    private static final String LOCATION = "FileBasedDatabase\\tables\\";

    public UpdateStatement(String tableName, String updates, String conditions) {
        this.statementType = "UPDATE";
        this.tableName = tableName;
        this.updates = updates;
        this.conditions = conditions;
    }

    @Override
    public void executeStatement() throws InvalidSQLStatementException{
        textFileReader reader = new textFileReader(LOCATION + this.tableName + FILE_SUFFIX);
        ArrayList<String> tableLines = reader.getLines();
        reader.closeIO();
        String[] tableHeaders = tableLines.get(0).split(",");
        StringBuilder outputTable = new StringBuilder();
        outputTable.append(tableLines.get(0));
        String[] updateSplited = updates.split("=");

        String updateWhat = updateSplited[0].trim();
        String updateTo = updateSplited[1].trim();
        int columnIndex = getColumnIndex(updateWhat, tableHeaders);

        for(int index = 1; index < tableLines.size(); index++) {
            if(conditions != null) {
                if(WhereKeyword.evaluateCondition(conditions, tableHeaders, tableLines.get(index).split(","))) {
                    String[] splitedLine = tableLines.get(index).split(",");
                    outputTable.append("\n");
                    for(int headerIndex = 0; headerIndex < splitedLine.length; headerIndex++) {
                        if(headerIndex == columnIndex) {
                            outputTable.append(updateTo.trim()).append(",");
                        } else {
                            outputTable.append(splitedLine[headerIndex].trim()).append(",");
                        }
                    }
                    outputTable.setLength(outputTable.length() - 1);
                } else {
                    outputTable.append("\n");
                    outputTable.append(tableLines.get(index));
                }
            } else {
                outputTable.append("\n");
                String[] splitedLine = tableLines.get(index).split(",");
                for(int headerIndex = 0; headerIndex < splitedLine.length; headerIndex++) {
                    if(headerIndex == columnIndex) {
                        outputTable.append(updateTo.trim()).append(",");
                    } else {
                        outputTable.append(splitedLine[headerIndex].trim()).append(",");
                    }
                }
            }
        }
        textFileWriter writer = new textFileWriter(LOCATION + this.tableName + FILE_SUFFIX, false);
        writer.writeLine(outputTable.toString());
        writer.closeIO();
    }

    private static int getColumnIndex(String columnName, String[] tableHeaders) throws InvalidSQLStatementException {
        for (int index = 0; index < tableHeaders.length; index++) {
            if(tableHeaders[index].trim().equals(columnName)) {
                return index;
            }
        }
        throw new InvalidSQLStatementException("Error: Column " + columnName + " is not in table headers.", columnName);
    }
}

class DeleteStatement extends SQLStatement {
    private String conditions;
    private static final String FILE_SUFFIX = ".txt";
    private static final String LOCATION = "FileBasedDatabase\\tables\\";

    public DeleteStatement(String tableName, String conditions) {
        this.statementType = "DELETE";
        this.tableName = tableName;
        this.conditions = conditions;
    }

    @Override
    public void executeStatement() throws InvalidSQLStatementException{
        textFileReader reader = new textFileReader(LOCATION + this.tableName + FILE_SUFFIX);
        ArrayList<String> tableLines = reader.getLines();
        reader.closeIO();
        String[] tableHeaders = tableLines.get(0).split(",");
        StringBuilder outputTable = new StringBuilder();
        outputTable.append(tableLines.get(0));
        for(int index = 1; index < tableLines.size(); index++) {
            if(conditions != null) {
                if(!WhereKeyword.evaluateCondition(conditions, tableHeaders, tableLines.get(index).split(","))) {
                    outputTable.append("\n");
                    outputTable.append(tableLines.get(index));
                }
            } else {
                outputTable.append(tableLines.get(index));
            }
        }
        textFileWriter writer = new textFileWriter(LOCATION + this.tableName + FILE_SUFFIX, false);
        writer.writeLine(outputTable.toString());
        writer.closeIO();
    }
}

class WhereKeyword {
    public static boolean evaluateCondition(String conditions, String[] tableHeaders, String[] tableValues) throws InvalidSQLStatementException{
        String[] conditionsSplited = conditions.trim().split("\\s+");
        if (conditionsSplited.length != 3) {
            throw new InvalidSQLStatementException("Error: Condition input is invalid: " + conditions, conditions);
        }
        String columnName = conditionsSplited[0].trim();
        String operator = conditionsSplited[1].trim();
        String comparedValue = conditionsSplited[2].trim();
        int columnIndex = getColumnIndex(columnName, tableHeaders);
        return compareValue(operator, comparedValue, tableValues, columnIndex);
    }

    private static boolean compareValue(String operator, String comparedValue, String[] tableValues, int columnIndex) throws InvalidSQLStatementException{
        if ("==".equals(operator)) {
            return tableValues[columnIndex].trim().equals(comparedValue.trim());
        } else if ("!=".equals(operator)) {
            return !tableValues[columnIndex].trim().equals(comparedValue.trim());
        } else if (">=".equals(operator)) {
            try {
                int intTableValue = Integer.parseInt(tableValues[columnIndex].trim());
                int intComparedValue = Integer.parseInt(comparedValue.trim());
              return intTableValue >= intComparedValue;
            } catch (NumberFormatException e) {
              return false;
            }
        } else if ("<=".equals(operator)) {
                    try {
                        int intTableValue = Integer.parseInt(tableValues[columnIndex].trim());
                        int intComparedValue = Integer.parseInt(comparedValue.trim());
                    return intTableValue <= intComparedValue;
                    } catch (NumberFormatException e) {
                    return false;
                
                }
        } else if (">".equals(operator)) {
            try {
                int intTableValue = Integer.parseInt(tableValues[columnIndex].trim());
                int intComparedValue = Integer.parseInt(comparedValue.trim());
                return intTableValue > intComparedValue;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if ("<".equals(operator)) {
            try {
              int intTableValue = Integer.parseInt(tableValues[columnIndex].trim());
              int intComparedValue = Integer.parseInt(comparedValue.trim());
              return intTableValue < intComparedValue;
            } catch (NumberFormatException e) {
              return false;
            }
        }  else {
            throw new InvalidSQLStatementException("Error: Operator " + operator + " is not valid.", operator);
        }
    }

    private static int getColumnIndex(String columnName, String[] tableHeaders) throws InvalidSQLStatementException {
        for (int index = 0; index < tableHeaders.length; index++) {
            if(tableHeaders[index].trim().equals(columnName)) {
                return index;
            }
        }
        throw new InvalidSQLStatementException("Error: Column" + columnName + " is not in table headers.", columnName);
    }
}

class CreateStatement extends SQLStatement {
    private static final String FILE_SUFFIX = ".txt";
    private static final String LOCATION = "FileBasedDatabase\\tables\\";
    private String headers;

    public CreateStatement(String tableName, String headers) {
        this.tableName = tableName;
        this.headers = headers;
    }

    @Override
    public void executeStatement() throws InvalidSQLStatementException {
        checkFile(tableName);
        textFileWriter writer = new textFileWriter(LOCATION + tableName + FILE_SUFFIX, false);
        writer.writeLine(this.headers.trim());
        writer.closeIO();
    }

    private void checkFile(String tableName) throws InvalidSQLStatementException{
        File file = new File(LOCATION + tableName + FILE_SUFFIX);
        if (file.exists()) {
            throw new InvalidSQLStatementException("Error: Table " + tableName + " already exists.", tableName);
        }
    }
}

class DropStatement extends SQLStatement {
    private static final String FILE_SUFFIX = ".txt";
    private static final String LOCATION = "FileBasedDatabase\\tables\\";

    public DropStatement(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void executeStatement() throws InvalidSQLStatementException {
        checkFile(tableName);
        File tableToDrop = new File(LOCATION + tableName + FILE_SUFFIX);
        tableToDrop.delete();
    }

    private void checkFile(String tableName) throws InvalidSQLStatementException{
        File file = new File(LOCATION + tableName + FILE_SUFFIX);
        if (!file.exists()) {
            throw new InvalidSQLStatementException("Error: Table " + tableName + " doesn't exist.", tableName);
        }
    }
}