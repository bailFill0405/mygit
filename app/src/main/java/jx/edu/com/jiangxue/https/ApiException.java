package jx.edu.com.jiangxue.https;

public class ApiException extends RuntimeException {

    private int errorCode;

    public ApiException(int code, String msg) {
        super(msg);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
