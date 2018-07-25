package cn.kim.exception;

/**
 * 全局异常处理
 */
public class CustomException extends Exception {

    //异常信息
    private String message;
    private String event;

    public CustomException(String message) {
        super(message);
        this.message = message;

    }

    public CustomException(String message, String event) {
        this.message = message;
        this.event = event;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
