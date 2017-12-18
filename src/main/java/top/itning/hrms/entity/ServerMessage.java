package top.itning.hrms.entity;

/**
 * 异常消息实体
 *
 * @author wangn
 */
public class ServerMessage {
    public static final int NOT_FIND = 404;
    public static final int SERVICE_ERROR = 500;
    public static final int SUCCESS_CODE = 200;
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误消息
     */
    private String msg;
    /**
     * 发生错误的URL
     */
    private String url;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
