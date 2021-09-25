package mitin.backend.system.model;

public class Response {
    TypeResult result;
    String msg;

    public Response(TypeResult result) {
        this.result = result;
    }

    public Response(TypeResult result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public TypeResult getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }
}
