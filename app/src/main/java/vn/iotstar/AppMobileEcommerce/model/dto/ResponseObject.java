package vn.iotstar.AppMobileEcommerce.model.dto;


import vn.iotstar.AppMobileEcommerce.model.CustomerModel;

public class ResponseObject <T>{
    private String status;
    private String message;

    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {    
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseObject(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseObject{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
