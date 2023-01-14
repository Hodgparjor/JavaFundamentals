package FileBasedDatabase;

public class InvalidSQLStatementException extends Exception {
    private String invalidStatement;

    public InvalidSQLStatementException(String message, String statement) {
        super(message);
        this.invalidStatement = statement;
    }

    public String getInvalidStatement() {
        return this.invalidStatement;
    }
}
