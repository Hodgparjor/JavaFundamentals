package FileBasedDatabase;
import java.util.*;
import java.util.regex.*;


class StatementMatcher {
    private static final String SELECT_REGEX = "^SELECT\\s+(.+?)\\s+FROM\\s+(\\S+)(?:\\s+WHERE\\s+)?(.+?)?$";
    private static final String INSERT_REGEX = "^INSERT\\s+INTO\\s+(\\S+)\\s+VALUES\\s+(.+?)$";
    private static final String UPDATE_REGEX = "^UPDATE\\s+(\\S+)\\s+SET\\s+(\\S+)\\s*=\\s*(\\S+)(?:\\s+WHERE\\s+)?(.+?)?$";
    private static final String DELETE_REGEX = "^DELETE\\s+FROM\\s+(\\S+)(?:\\s+WHERE\\s+)?(.+?)?$";
    private static final Pattern SELECT_PATTERN = Pattern.compile(SELECT_REGEX);
    private static final Pattern INSERT_PATTERN = Pattern.compile(INSERT_REGEX);
    private static final Pattern UPDATE_PATTERN = Pattern.compile(UPDATE_REGEX);
    private static final Pattern DELETE_PATTERN = Pattern.compile(DELETE_REGEX);

    private String statementType;
    private ArrayList<String> statementParameters;

    public StatementMatcher(String insertedStatement) throws InvalidSQLStatementException {
        Matcher selectMatcher = SELECT_PATTERN.matcher(insertedStatement);
        Matcher insertMatcher = INSERT_PATTERN.matcher(insertedStatement);
        Matcher updateMatcher = UPDATE_PATTERN.matcher(insertedStatement);
        Matcher deleteMatcher = DELETE_PATTERN.matcher(insertedStatement);
        this.statementParameters = new ArrayList<String>();

        if (selectMatcher.matches()) {
            this.statementType = "SELECT";
            this.statementParameters.add(selectMatcher.group(2)); // tableName
            this.statementParameters.add(selectMatcher.group(1)); // columns
            this.statementParameters.add(selectMatcher.group(3)); // conditions
        } else if (insertMatcher.matches()) {
            this.statementType = "INSERT";
            this.statementParameters.add(insertMatcher.group(1)); // tableName
            this.statementParameters.add(insertMatcher.group(2)); // values
        } else if (updateMatcher.matches()) {
            this.statementType = "UPDATE";
            this.statementParameters.add(updateMatcher.group(1)); // tableName
            this.statementParameters.add(updateMatcher.group(2) + " = " + updateMatcher.group(3)); // updates
            this.statementParameters.add(updateMatcher.group(4)); // conditions
        } else if (deleteMatcher.matches()) {
            this.statementType = "DELETE";
            this.statementParameters.add(deleteMatcher.group(1)); // tableName
            this.statementParameters.add(deleteMatcher.group(2)); // condition
        } else {
            throw new InvalidSQLStatementException("Error: Inserted SQL statement is invalid.", insertedStatement);
        }
    }

    public String getStatementType() {
        return statementType;
    }

    public ArrayList<String> getStatementParameters() {
        return statementParameters;
    }
}
