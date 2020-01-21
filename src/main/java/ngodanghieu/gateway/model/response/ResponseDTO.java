package ngodanghieu.gateway.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Meta meta;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Body body;

    public ResponseDTO(String code ,String message) {
        if (code != null && message != null)
            this.meta = new Meta(code,message);
    }

    public ResponseDTO(String code ,String message, String user_name) {
        if (code != null && message != null)
            this.meta = new Meta(code,message);

        if (user_name != null)
            this.body = new Body(user_name);
    }

    public ResponseDTO(String code ,String message, Object object) {
        if (code != null && message != null)
            this.meta = new Meta(code,message);

        if (object != null)
            this.body = new Body(object);
    }

    public ResponseDTO(String code ,String message,String user_name, Object object) {
        if (code != null && message != null)
            this.meta = new Meta(code,message);

            this.body = new Body(user_name,object);
    }
}

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Meta {
    private String code;
    private String message;
}

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Body {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String user_name;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("data")
    private Object data;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("function_name")
    private String function_name;

    public Body(String user_name, Object data) {
        this.user_name = user_name;
        this.data = data;
    }

    public Body(String user_name) {
        this.user_name = user_name;
    }

    public Body(Object object) {
        this.data = object;
    }
}

