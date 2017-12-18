package top.itning.hrms.exception.defaults;

/**
 * 空参数异常
 *
 * @author Ning
 */
public class NullParameterException extends DefaultException {
    /**
     * 异常消息
     */
    private String exceptionMessage;

    public NullParameterException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
