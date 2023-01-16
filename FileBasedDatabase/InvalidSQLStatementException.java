package FileBasedDatabase;

public class InvalidSQLStatementException extends Exception {
    private String invalidData;

    public InvalidSQLStatementException(String message, String data) {
        super(message);
        this.invalidData = data;
    }

    public String getInvalidData() {
        return this.invalidData;
    }
}
