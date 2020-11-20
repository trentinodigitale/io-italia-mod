package it.tndigit.iot.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class IotException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 285218528627583070L;
    protected String errorCode;
    protected String message;
    protected String classe;
    protected String metodo;
    protected String custom;

    public IotException(){

    }
    public IotException(String message) {
        super();
        this.setMessage(message);
        this.custom="";
        this.metodo="";
        this.classe="";
    }

    public IotException(String errorCode, String message) {
        super();
        this.setMessage(message);
        this.setErrorCode(errorCode);
        this.custom="";
        this.metodo="";
        this.classe="";
    }

    public IotException(String message, String classe, String metodo, String custom) {
        super();
        this.classe =classe;
        this.metodo=metodo;
        this.custom=custom;
        this.setMessage(message);
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "IotException{" +
                "errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                ", classe='" + classe + '\'' +
                ", metodo='" + metodo + '\'' +
                ", custom='" + custom + '\'' +
                '}';
    }


    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
