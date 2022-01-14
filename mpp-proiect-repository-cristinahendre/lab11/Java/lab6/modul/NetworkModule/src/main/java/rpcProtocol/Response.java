package rpcProtocol;


import java.io.Serializable;


public class Response implements Serializable {
    private ResponseTime type;
    private Object data;

    private Response(){};

    public ResponseTime type(){
        return type;
    }

    public Object data(){
        return data;
    }

    private void type(ResponseTime type){
        this.type=type;
    }

    public void data(Object data){
        this.data=data;
    }

    @Override
    public String toString(){
        return "Response{" +
                "type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }


    public static class Builder{
        private Response response=new Response();

        public Builder type(ResponseTime type) {
            response.type(type);
            return this;
        }

        public Builder data(Object data) {
            response.data(data);
            return this;
        }

        public Response build() {
            return response;
        }
    }

}
