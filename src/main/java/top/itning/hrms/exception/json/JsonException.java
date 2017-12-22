package top.itning.hrms.exception.json;

/**
 * Json异常
 *
 * @author Ning
 */
public class JsonException extends Exception {
    /**
     * 错误代码
     */
    private int code;

    public JsonException(String exceptionMessage, int code) {
        super(exceptionMessage);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
