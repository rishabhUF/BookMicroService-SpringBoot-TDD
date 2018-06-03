package rishabh.demo2.Exception;

import java.util.UUID;

public class BookPlaceException extends RuntimeException{
    private Integer code = 500;
    private String errorID = UUID.randomUUID().toString();
    private String userMessage;

    public BookPlaceException(){
        super();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
}
