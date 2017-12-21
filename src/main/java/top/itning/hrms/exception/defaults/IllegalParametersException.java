package top.itning.hrms.exception.defaults;

/**
 * 非法参数异常
 *
 * @author Ning
 */
public class IllegalParametersException extends DefaultException {
    /**
     * 异常消息
     */
    private String exceptionMessage;

    public IllegalParametersException(String exceptionMessage) {
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
