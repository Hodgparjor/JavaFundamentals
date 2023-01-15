package FileBasedDatabase;

public class DataNotMatchWithHeadersException extends Exception {
    public DataNotMatchWithHeadersException(String message) {
        super(message);
    }
}
