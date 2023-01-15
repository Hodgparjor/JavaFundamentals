package FileBasedDatabase;

public class InvalidSQLStatementException extends Exception {
    private String invalidData;

    public InvalidSQLStatementException(String message, String statement) {
        super(message);
        this.invalidData = statement;
    }

    public String getInvalidData() {
        return this.invalidData;
    }
}
