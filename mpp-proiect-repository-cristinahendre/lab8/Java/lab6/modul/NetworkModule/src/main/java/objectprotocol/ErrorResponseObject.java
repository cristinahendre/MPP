package objectprotocol;


public class ErrorResponseObject implements ResponseObject {
    private String message;

    public ErrorResponseObject(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
