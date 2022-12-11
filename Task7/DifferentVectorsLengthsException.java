package Task7;

public class DifferentVectorsLengthsException extends Exception {
    private String[] exceptionData;

    public DifferentVectorsLengthsException(String message, String[] exceptionData) {
        super(message);
        this.exceptionData = exceptionData;
    }

    public String[] getExceptionData() {
        return this.exceptionData;
    }
}
