package top.itning.hrms.exception.defaults;

/**
 * 没有这个ID异常
 *
 * @author Ning
 */
public class NoSuchIdException extends DefaultException {
    /**
     * 异常消息
     */
    private String exceptionMessage;

    public NoSuchIdException(String exceptionMessage) {
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
